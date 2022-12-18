package holdMyBeer.query.rest;

import com.proto.prime.BeerServiceGrpc;
import com.proto.prime.QueryBeersRequest;
import com.proto.prime.QueryBeersResponse;
import io.grpc.ManagedChannel;
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
    @GetMapping
    public boolean queryBeersDecomposition() {

        QueryBeersRequest beersRequest = QueryBeersRequest.newBuilder().build();
        return blockingStub.queryBeersDecomposition(beersRequest).next().getIsSuccess();


    }
    // Read Beer By id
    @GetMapping("/{id}")
    public void queryBeerByIdDecomposition(@PathVariable String id) {
//        return beerService.queryBeerByIdDecomposition(id);
    }



}

