package holdMyBeer.service;

import com.google.protobuf.ProtocolStringList;
import com.proto.prime.*;
import holdMyBeer.database.pojo.BeerDB;
import holdMyBeer.database.pojo.data.IngredientDB;
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
            BeerDB beerDB = new BeerDB(request.getName(), request.getDescription(), ingredientsDB, methods);
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

            QueryBeersResponse response =QueryBeersResponse.newBuilder()
                    .setIsSuccess(true)
                    .build();
            responseObserver.onNext(response);
        } catch(Exception e){
            QueryBeersResponse response =QueryBeersResponse.newBuilder()
                    .setIsSuccess(false)
                    .build();
            responseObserver.onNext(response);
        }

        responseObserver.onCompleted();


    }


    @Override
    public void queryBeerByIdDecomposition(QueryBeerByIdRequest request, StreamObserver<QueryBeerByIdResponse> responseObserver) {
        try{
            BeerDB beerDB = beerRepository.findBeerByID(request.getId());
            QueryBeerByIdResponse response = QueryBeerByIdResponse.newBuilder()
                    .setIsSuccess(true)
                    .build();
            responseObserver.onNext(response);
        }catch (Exception e){
            QueryBeerByIdResponse response = QueryBeerByIdResponse.newBuilder()
                    .setIsSuccess(false)
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
            BeerDB beerDB = new BeerDB(request.getId(),request.getName(), request.getDescription(), ingredientsDB, methods);
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
