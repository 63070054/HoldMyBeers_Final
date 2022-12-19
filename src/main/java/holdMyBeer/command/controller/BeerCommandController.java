package holdMyBeer.command.controller;

import com.proto.prime.*;
import holdMyBeer.command.rest.CreateBeerRestModel;
import holdMyBeer.command.rest.DeleteBeerRestModel;
import holdMyBeer.command.rest.UpdateBeerRestModel;
import io.grpc.ManagedChannel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.UUID;

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
    @RabbitListener(queues = "CreateBeerQueue")
    @PostMapping
    public boolean createBeer(@RequestBody CreateBeerRestModel beer){

            CreateBeerRequest request = CreateBeerRequest.newBuilder()
                    .setName(beer.getName())
                    .setDescription(beer.getDescription())
                    .addAllIngredients(beer.getIngredients())
                    .addAllMethods(Arrays.asList(beer.getMethods()))
                    .build();

//            CreateBeerCommand command = CreateBeerCommand.builder()._id(UUID.randomUUID().toString()).name(beer.getName()).description(beer.getDescription()).ingredients(beer.getIngredients()).methods(beer.getMethods()).build();
//
//            try{
//                commandGateway.sendAndWait(command);
//            }catch (Exception e){
//              e.getLocalizedMessage();
//            }
            return beerServiceBlockingStub.createBeerDecomposition(request).getIsSuccess();


    }
    // Update Beer
    @RabbitListener(queues = "EditBeerQueue")
    @PutMapping()
    public boolean updateBeer(@RequestBody UpdateBeerRestModel beer) {
        EditBeerRequest request = EditBeerRequest.newBuilder()
                .setLocalId(beer.get_id())
                .setName(beer.getName())
                .setDescription(beer.getDescription())
                .addAllIngredients(beer.getIngredients())
                .addAllMethods(Arrays.asList(beer.getMethods()))
                .build();
        return beerServiceBlockingStub.editBeerDecomposition(request).getIsSuccess();
    }

    // Delete Beer
    @RabbitListener(queues = "DeleteBeerQueue")
    @DeleteMapping()
    public boolean deleteBeer(@RequestBody DeleteBeerRestModel beer) {
        DeleteBeerRequest request = DeleteBeerRequest.newBuilder()
                .setId(beer.get_id())
                .build();
        return beerServiceBlockingStub.deleteBeerDecomposition(request).getIsSuccess();
    }


}