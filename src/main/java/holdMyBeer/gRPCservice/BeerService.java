package holdMyBeer.gRPCservice;

import com.google.protobuf.ProtocolStringList;
import com.proto.prime.*;
import holdMyBeer.database.pojo.BeerDB;
import holdMyBeer.database.pojo.IngredientDB;
import holdMyBeer.database.repository.BeerRepository;
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
            System.out.println(beerDB.get_id());
            BeerQueryResponse newBeersUser = BeerQueryResponse.newBuilder()
                    .setId(beerDB.get_id())
                    .setName(beerDB.getName())
                    .setDescription(beerDB.getDescription())
                    .addAllIngredients(convertIngredientDBToQueryResponse(beerDB.getIngredients()))
                    .addAllMethods(Arrays.asList(beerDB.getMethods()))
                    .setImageUrl(beerDB.getImageUrl())
                    .build();
            System.out.println(newBeersUser);
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
            String[] methods = convertMethodsRequestToDB(request.getMethodsList());
            List<IngredientDB> ingredientsDB = convertIngredientRequestToDB(request.getIngredientsList());
            BeerDB beerDB = new BeerDB(request.getName(), request.getDescription(), ingredientsDB, methods, request.getImageUrl());
            beerRepository.insert(beerDB);
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
            System.out.println("GRPC Error");
            System.out.println(e);
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
            BeerDB beerDB = beerRepository.findBeerByID(request.getId());
            BeerQueryResponse beer = BeerQueryResponse.newBuilder()
                    .setId(beerDB.get_id())
                    .setName(beerDB.getName())
                    .setDescription(beerDB.getDescription())
                    .addAllIngredients(convertIngredientDBToQueryResponse(beerDB.getIngredients()))
                    .addAllMethods(Arrays.asList(beerDB.getMethods()))
                    .setImageUrl(beerDB.getImageUrl())
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
            String[] methods = convertMethodsRequestToDB(request.getMethodsList());
            List<IngredientDB> ingredientsDB = convertIngredientRequestToDB(request.getIngredientsList());
            BeerDB beerDB = new BeerDB(request.getId(),request.getName(), request.getDescription(), ingredientsDB, methods, request.getImageUrl());
            beerRepository.save(beerDB);


            UpdateBeerResponse response = UpdateBeerResponse.newBuilder()
                    .setIsSuccess(true)
                    .build();
            responseObserver.onNext(response);
        }catch (Exception e){
            UpdateBeerResponse response = UpdateBeerResponse.newBuilder()
                    .setIsSuccess(false)
                    .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }


    @Override
    public void deleteBeerDecomposition(DeleteBeerRequest request, StreamObserver<DeleteBeerResponse> responseObserver) {
        try{
            BeerDB beerDB = beerRepository.findBeerByID(request.getId());
            beerRepository.delete(beerDB);
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
