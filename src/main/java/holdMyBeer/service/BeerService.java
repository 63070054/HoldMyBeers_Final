package holdMyBeer.service;

import com.proto.prime.*;
import holdMyBeer.database.pojo.Beer;
import holdMyBeer.database.repository.BeerRepository;
import io.grpc.stub.StreamObserver;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BeerService extends BeerServiceGrpc.BeerServiceImplBase {
    @Autowired
    private BeerRepository beerRepository;


    @Override
    public void createBeerDecomposition(CreateBeerRequest request, StreamObserver<CreateBeerResponse> responseObserver) {
//        boolean isSuccess = true;
//        try{
//            beerRepository.insert(request);
//        }catch (Exception e){
//            isSuccess = false;
//        }
//        CreateBeerResponse response = CreateBeerResponse.newBuilder()
//                .setIsSuccess(isSuccess)
//                .build();
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
    }


    @Override
    public void queryBeersDecomposition(QueryBeersRequest request, StreamObserver<QueryBeersResponse> responseObserver) {
        boolean isSuccess = true;
        try {
            List<Beer> beers = beerRepository.findAll();

            for(Beer beer : beers) {
                System.out.println("BeerName : " + beer.getName());
            }

        } catch(Exception e){
           isSuccess = false;
        }
        QueryBeersResponse response =QueryBeersResponse.newBuilder()
                .setIsSuccess(isSuccess)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();


    }


    @Override
    public void queryBeerByIdDecomposition(QueryBeerByIdRequest request, StreamObserver<QueryBeerByIdResponse> responseObserver) {
//        boolean isSuccess = true;
//        try{
//            Beer beer = beerRepository.findBeerByID(request.getID());
//            System.out.println("BearName: "+ beer.getName());
//        }catch (Exception e){
//            isSuccess = false;
//        }
//        QueryBeerByIdResponse response = QueryBeerByIdResponse.newBuilder()
//                .setIsSuccess(isSuccess)
//                .build();
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
    }

    @Override
    public void editBeerDecomposition(EditBeerRequest request, StreamObserver<EditBeerResponse> responseObserver) {
//        boolean isSuccess = true;
//        try{
//            beerRepository.save(request);
//        }catch (Exception e){
//        isSuccess = false;
//        }
//        QueryBeersResponse response =QueryBeersResponse.newBuilder()
//                .setIsSuccess(isSuccess)
//                .build();
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
    }

    @Override
    public void deleteBeerDecomposition(DeleteBeerRequest request, StreamObserver<DeleteBeerResponse> responseObserver) {
//        boolean isSuccess = true;
//        try{
//            beerRepository.delete(request);
//        }catch (Exception e){
//            isSuccess = false;
//        }
//        DeleteBeerResponse response = DeleteBeerResponse.newBuilder()
//                .setIsSuccess(isSuccess)
//                .build();
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
    }
}
