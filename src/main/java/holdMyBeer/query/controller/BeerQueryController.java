package holdMyBeer.query.controller;

import com.proto.prime.BeerServiceGrpc;
import com.proto.prime.QueryBeerByIdRequest;
import com.proto.prime.QueryBeersRequest;
import com.proto.prime.QueryBeersResponse;
import io.grpc.ManagedChannel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/beers")
public class BeerQueryController {

    private final BeerServiceGrpc.BeerServiceBlockingStub blockingStub;
    private final BeerServiceGrpc.BeerServiceStub stub;

    public BeerQueryController(ManagedChannel channel) {
        this.blockingStub = BeerServiceGrpc.newBlockingStub(channel);
        this.stub = BeerServiceGrpc.newStub(channel);
    }


    // Read All Beer
    @RabbitListener(queues = "QueryBeersQueue")
    @GetMapping
    public boolean queryBeersDecomposition() {

        QueryBeersRequest beersRequest = QueryBeersRequest.newBuilder().build();
        boolean isSuccess = blockingStub.queryBeersDecomposition(beersRequest).next().getIsSuccess();
        return isSuccess;


    }
    // Read Beer By id
    @RabbitListener(queues = "QueryBeerByIdQueue")
    @GetMapping("/{id}")
    public boolean queryBeerByIdDecomposition(@PathVariable String id) {
        QueryBeerByIdRequest beersRequest = QueryBeerByIdRequest.newBuilder().setId(id).build();
        boolean isSuccess = blockingStub.queryBeerByIdDecomposition(beersRequest).getIsSuccess();
        return isSuccess;
    }



}

