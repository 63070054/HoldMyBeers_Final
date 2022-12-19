package holdMyBeer.command.controller;

import com.proto.prime.*;
import holdMyBeer.command.CreateBeerCommand;
import holdMyBeer.command.UpdateBeerCommand;
import holdMyBeer.command.rest.CreateBeerRestModel;
import holdMyBeer.command.rest.DeleteBeerRestModel;
import holdMyBeer.command.rest.UpdateBeerRestModel;
import holdMyBeer.database.pojo.data.IngredientDB;
import io.grpc.ManagedChannel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/beers")
public class BeerCommandController {



    private final AuthenticationServiceGrpc.AuthenticationServiceBlockingStub authenticationServiceBlockingStub;
    private final BeerServiceGrpc.BeerServiceBlockingStub beerServiceBlockingStub;
    private final FavoriteBeerServiceGrpc.FavoriteBeerServiceBlockingStub favoriteBeerServiceBlockingStub;
    @Autowired
    private CommandGateway commandGateway;

    public BeerCommandController(ManagedChannel channel) {
        this.authenticationServiceBlockingStub = AuthenticationServiceGrpc.newBlockingStub(channel);
        this.beerServiceBlockingStub = BeerServiceGrpc.newBlockingStub(channel);
        this.favoriteBeerServiceBlockingStub = FavoriteBeerServiceGrpc.newBlockingStub(channel);
    }


    // Create Beer
    @PostMapping
    public boolean createBeer(@RequestBody CreateBeerRestModel beer){

            CreateBeerCommand command = CreateBeerCommand.builder()
                    ._id(UUID.randomUUID().toString())
                    .name(beer.getName())
                    .description(beer.getDescription())
                    .ingredients(beer.getIngredients())
                    .methods(beer.getMethods())
                    .build();
            return commandGateway.sendAndWait(command);
    }
    // Update Beer
    @PutMapping()
    public boolean updateBeer(@RequestBody UpdateBeerRestModel beer) {

        UpdateBeerCommand command = UpdateBeerCommand.builder()
                ._id(UUID.randomUUID().toString())
                .name(beer.getName())
                .description(beer.getDescription())
                .ingredients(beer.getIngredients())
                .methods(beer.getMethods())
                .build();
        return commandGateway.sendAndWait(command);
    }

    // Delete Beer
    @DeleteMapping()
    public boolean deleteBeer(@RequestBody DeleteBeerRestModel beer) {
        DeleteBeerRequest request = DeleteBeerRequest.newBuilder()
                .setId(beer.get_id())
                .build();
        return beerServiceBlockingStub.deleteBeerDecomposition(request).getIsSuccess();
    }


}