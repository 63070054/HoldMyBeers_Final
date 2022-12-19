package holdMyBeer.service;

import com.google.protobuf.ProtocolStringList;
import com.proto.prime.*;
import holdMyBeer.command.CreateBeerCommand;
import holdMyBeer.database.pojo.Beer;
import holdMyBeer.database.repository.BeerRepository;
import io.grpc.stub.StreamObserver;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Service
public class BeerService extends BeerServiceGrpc.BeerServiceImplBase {
    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void createBeerDecomposition(CreateBeerRequest request, StreamObserver<CreateBeerResponse> responseObserver) {
        try{

            ProtocolStringList stringList = request.getMethodsList();
            Object[] objectArray = stringList.toArray();
            String[] methods = Arrays.copyOf(objectArray, objectArray.length, String[].class);

            CreateBeerCommand command = CreateBeerCommand.builder()
                    ._id(UUID.randomUUID().toString())
                    .name(request.getName())
                    .description(request.getDescription())
                    .ingredients(request.getIngredientsList())
                    .methods(methods)
                    .build();

            // create object beer
            Beer beer = new Beer(request.getName(), request.getDescription(), request.getIngredientsList(), methods);

            // rabbitMQ
            rabbitTemplate.convertSendAndReceive("BeerExchanges","create",beer);

            // insert beer into MongoDB
            beerRepository.insert(beer);

            // sent response
            CreateBeerResponse response = CreateBeerResponse.newBuilder()
                    .setIsSuccess(true)
                    .build();
            responseObserver.onNext(response);

        }catch (Exception e){
            System.out.println(e);

            // sent response
            CreateBeerResponse response = CreateBeerResponse.newBuilder()
                    .setIsSuccess(false)
                    .build();
            responseObserver.onNext(response);
        }

        //finish progress
        responseObserver.onCompleted();
    }


    @Override
    public void queryBeersDecomposition(QueryBeersRequest request, StreamObserver<QueryBeersResponse> responseObserver) {
        try {
            List<Beer> beers = beerRepository.findAll();

            //rabbitMQ (if you want to get data allbeer you can use this too )
            rabbitTemplate.convertSendAndReceive("QueryBeersQueue","queryAll","");

            for(Beer beer : beers) {
                System.out.println("BeerName : " + beer.getName());
            }

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
            Beer beer = beerRepository.findBeerByID(request.getId());

            //rabbitMQ (if you want to get data allbeer you can use this too by define Beer beer)
            rabbitTemplate.convertSendAndReceive("QueryBeerByIdQueue","queryById",request.getId());

            System.out.println("BearName: "+ beer.getName());
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
    public void editBeerDecomposition(EditBeerRequest request, StreamObserver<EditBeerResponse> responseObserver) {
        try{
            ProtocolStringList stringList = request.getMethodsList();
            Object[] objectArray = stringList.toArray();
            String[] methods = Arrays.copyOf(objectArray, objectArray.length, String[].class);

            Beer beer = new Beer(request.getLocalId(),request.getName(), request.getDescription(), request.getIngredientsList(), methods);

            beerRepository.save(beer);

            //rabbitMQ
            rabbitTemplate.convertSendAndReceive("EditBeerQueue","edit",beer);

            EditBeerResponse response = EditBeerResponse.newBuilder()
                    .setIsSuccess(true)
                    .build();
            responseObserver.onNext(response);
        }catch (Exception e){
            EditBeerResponse response = EditBeerResponse.newBuilder()
                    .setIsSuccess(false)
                    .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void deleteBeerDecomposition(DeleteBeerRequest request, StreamObserver<DeleteBeerResponse> responseObserver) {
        try{
            Beer beer = beerRepository.findBeerByID(request.getId());
            beerRepository.delete(beer);

            //rabbitMQ
            rabbitTemplate.convertSendAndReceive("DeleteBeerQueue","delete",request.getId());

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
