package holdMyBeer.command.controller;

import com.proto.prime.*;
import holdMyBeer.command.rest.CreateBeerRestModel;
import holdMyBeer.command.rest.DeleteBeerRestModel;
import holdMyBeer.command.rest.UpdateBeerRestModel;
import io.grpc.ManagedChannel;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/beers")
public class BeerCommandController {

    private final AuthenticationServiceGrpc.AuthenticationServiceBlockingStub authenticationServiceBlockingStub;
    private final BeerServiceGrpc.BeerServiceBlockingStub beerServiceBlockingStub;
    private final FavoriteBeerServiceGrpc.FavoriteBeerServiceBlockingStub favoriteBeerServiceBlockingStub;

    public BeerCommandController(ManagedChannel channel) {
        this.authenticationServiceBlockingStub = AuthenticationServiceGrpc.newBlockingStub(channel);
        this.beerServiceBlockingStub = BeerServiceGrpc.newBlockingStub(channel);
        this.favoriteBeerServiceBlockingStub = FavoriteBeerServiceGrpc.newBlockingStub(channel);
    }

    // Create Beer
    @PostMapping
    public boolean createBeer(@RequestBody CreateBeerRestModel beer){

            CreateBeerRequest request = CreateBeerRequest.newBuilder()
                    .setName(beer.getName())
                    .setDescription(beer.getDescription())
                    .addAllIngredients(beer.getIngredients())
                    .addAllMethods(Arrays.asList(beer.getMethods()))
                    .build();
            return beerServiceBlockingStub.createBeerDecomposition(request).getIsSuccess();

    }
    // Update Beer
    @PutMapping()
    public void updateBeer(@RequestBody UpdateBeerRestModel beerRestModel) {
//        return beerService.updateBeer(id, beerRestModel);
    }

    // Delete Beer
    @DeleteMapping()
    public void deleteBeer(@RequestBody DeleteBeerRestModel beerRestModel) {
//        beerService.deleteQuery(id);
    }


}