package holdMyBeer.service;

import com.google.protobuf.ProtocolStringList;
import com.proto.prime.*;
import holdMyBeer.database.pojo.BeerDB;
import holdMyBeer.database.pojo.IngredientDB;
import holdMyBeer.database.pojo.UserDB;
import holdMyBeer.database.repository.BeerRepository;
import holdMyBeer.database.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class BeerService extends BeerServiceGrpc.BeerServiceImplBase {
    @Autowired
    private BeerRepository beerRepository;
    @Autowired
    private UserRepository userRepository;

    public List<IngredientQueryResponse> convertIngredientDBToQueryResponse(List<IngredientDB> ingredientsDB){
        List<IngredientQueryResponse> ingredientsResponse = new ArrayList<>();
        for (IngredientDB ingredient : ingredientsDB) {
            IngredientQueryResponse newIngredient = IngredientQueryResponse.newBuilder()
                    .setName(ingredient.getName())
                    .setQuantity(ingredient.getQuantity())
                    .setUnit(ingredient.getUnit())
                    .build();
            ingredientsResponse.add(newIngredient);
        }

        return ingredientsResponse;

    }

    public List<BeerQueryResponse> convertBeerDBToQueryResponse(List<BeerDB> beersDB){

        List<BeerQueryResponse> beersResponse = new ArrayList<>();
        for (BeerDB beerDB : beersDB) {
            BeerQueryResponse newBeersUser = BeerQueryResponse.newBuilder()
                    .setId(beerDB.get_id())
                    .setName(beerDB.getName())
                    .setDescription(beerDB.getDescription())
                    .addAllIngredients(convertIngredientDBToQueryResponse(beerDB.getIngredients()))
                    .addAllMethods(Arrays.asList(beerDB.getMethods()))
                    .setImageUrl(beerDB.getImageUrl())
                    .setUserId(beerDB.getUserId())
                    .build();
            beersResponse.add(newBeersUser);
        }

        return beersResponse;

    }

    public List<IngredientDB> convertIngredientRequestToDB(List<IngredientBeerRequest> ingredientsRequest){
        List<IngredientDB> ingredientsDB = new ArrayList<>();
        for (IngredientBeerRequest ingredient : ingredientsRequest) {
            IngredientDB newIngredient = new IngredientDB();
            newIngredient.setName(ingredient.getName());
            newIngredient.setQuantity(ingredient.getQuantity());
            newIngredient.setUnit(ingredient.getUnit());
            ingredientsDB.add(newIngredient);
        }

        return ingredientsDB;

    }

    public String[] convertMethodsRequestToDB(ProtocolStringList stringListRequest){
        Object[] objectArray = stringListRequest.toArray();
        String[] methods = Arrays.copyOf(objectArray, objectArray.length, String[].class);
        return methods;
    }


    @Override
    public void createBeerDecomposition(CreateBeerRequest request, StreamObserver<CreateBeerResponse> responseObserver) {
        try{
            String userId = request.getUserId();
            String[] methods = convertMethodsRequestToDB(request.getMethodsList());
            List<IngredientDB> ingredientsDB = convertIngredientRequestToDB(request.getIngredientsList());
            BeerDB beerDB = new BeerDB(request.getName(), request.getDescription(), ingredientsDB, methods, request.getImageUrl(), request.getUserId());
            beerRepository.insert(beerDB);
            UserDB userDB = userRepository.findUserById(userId);
            userDB.getOwner().add(beerDB);
            userRepository.save(userDB);


            CreateBeerResponse response = CreateBeerResponse.newBuilder()
                    .setIsSuccess(true)
                    .build();
            responseObserver.onNext(response);
        }catch (Exception e){
            CreateBeerResponse response = CreateBeerResponse.newBuilder()
                    .setIsSuccess(false)
                    .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }


    @Override
    public void queryBeersDecomposition(QueryBeersRequest request, StreamObserver<QueryBeersResponse> responseObserver) {
        try {
            List<BeerDB> beerDBS = beerRepository.findAll();
            QueryBeersResponse response = QueryBeersResponse.newBuilder()
                    .addAllBeers(convertBeerDBToQueryResponse(beerDBS))
                    .build();
            responseObserver.onNext(response);
        } catch(Exception e){
            QueryBeersResponse response =QueryBeersResponse.newBuilder()
                    .addAllBeers(null)
                    .build();
            responseObserver.onNext(response);
        }

        responseObserver.onCompleted();


    }


    @Override
    public void queryBeerByIdDecomposition(QueryBeerByIdRequest request, StreamObserver<QueryBeerByIdResponse> responseObserver) {
        try{
            BeerDB beerDB = beerRepository.findBeerByID(request.getBeerId());
            BeerQueryResponse beer = BeerQueryResponse.newBuilder()
                    .setId(beerDB.get_id())
                    .setName(beerDB.getName())
                    .setDescription(beerDB.getDescription())
                    .addAllIngredients(convertIngredientDBToQueryResponse(beerDB.getIngredients()))
                    .addAllMethods(Arrays.asList(beerDB.getMethods()))
                    .setImageUrl(beerDB.getImageUrl())
                    .setUserId(beerDB.getUserId())
                    .build();
            QueryBeerByIdResponse response = QueryBeerByIdResponse.newBuilder()
                    .setBeer(beer)
                    .build();
            responseObserver.onNext(response);
        }catch (Exception e){
            QueryBeerByIdResponse response = QueryBeerByIdResponse.newBuilder()
                    .setBeer((BeerQueryResponse) null)
                    .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void updateBeerDecomposition(UpdateBeerRequest request, StreamObserver<UpdateBeerResponse> responseObserver) {
        try{
            String beerId = request.getId();
            String userId = request.getUserId();
            String[] methods = convertMethodsRequestToDB(request.getMethodsList());
            List<IngredientDB> ingredientsDB = convertIngredientRequestToDB(request.getIngredientsList());
            BeerDB beerDB = new BeerDB(beerId,request.getName(), request.getDescription(), ingredientsDB, methods, request.getImageUrl(), userId);
            beerRepository.save(beerDB);
            UserDB userDB = userRepository.findUserById(userId);
            for (BeerDB beer : userDB.getOwner()) {
                updateBeerUser(beerId, beerDB, beer);
            }for (BeerDB beer : userDB.getFavorite()) {
                updateBeerUser(beerId, beerDB, beer);
            }
            userRepository.save(userDB);
            UpdateBeerResponse response = UpdateBeerResponse.newBuilder()
                    .setIsSuccess(true)
                    .build();
            responseObserver.onNext(response);
        }catch (Exception e){
            System.out.println(e);
            UpdateBeerResponse response = UpdateBeerResponse.newBuilder()
                    .setIsSuccess(false)
                    .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    private void updateBeerUser(String beerId, BeerDB beerDB, BeerDB beer) {
        if (beer.get_id().equals(beerId)) {
            beer.set_id(beerDB.get_id());
            beer.setName(beerDB.getName());
            beer.setDescription(beerDB.getDescription());
            beer.setIngredients(beerDB.getIngredients());
            beer.setMethods(beerDB.getMethods());
            beer.setImageUrl(beerDB.getImageUrl());
            beer.setUserId(beerDB.getUserId());
        }
    }


    @Override
    public void deleteBeerDecomposition(DeleteBeerRequest request, StreamObserver<DeleteBeerResponse> responseObserver) {
        try{
            String beerId = request.getBeerId();
            String userId = request.getUserId();
            BeerDB beerDB = beerRepository.findBeerByID(beerId);
            beerRepository.delete(beerDB);
            UserDB userDB = userRepository.findUserById(userId);
            userDB.getOwner().removeIf(beer -> beer.get_id().equals(beerId));
            userDB.getFavorite().removeIf(beer -> beer.get_id().equals(beerId));
            userRepository.save(userDB);
            DeleteBeerResponse response = DeleteBeerResponse.newBuilder()
                    .setIsSuccess(true)
                    .build();
            responseObserver.onNext(response);
        }catch (Exception e){
            DeleteBeerResponse response = DeleteBeerResponse.newBuilder()
                    .setIsSuccess(false)
                    .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }
}
