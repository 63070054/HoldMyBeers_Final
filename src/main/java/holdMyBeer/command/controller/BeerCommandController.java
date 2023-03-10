package holdMyBeer.command.controller;

import com.proto.prime.*;
import holdMyBeer.command.CreateBeerCommand;
import holdMyBeer.command.DeleteBeerCommand;
import holdMyBeer.command.UpdateBeerCommand;
import holdMyBeer.command.rest.CreateBeerRestModel;
import holdMyBeer.command.rest.DeleteBeerRestModel;
import holdMyBeer.command.rest.UpdateBeerRestModel;
import holdMyBeer.database.pojo.IngredientDB;
import io.grpc.ManagedChannel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/beers")
public class BeerCommandController {

    @Autowired
    private CommandGateway commandGateway;
    private final BeerServiceGrpc.BeerServiceBlockingStub beerServiceBlockingStub;
    public BeerCommandController(ManagedChannel channel){
        this.beerServiceBlockingStub = BeerServiceGrpc.newBlockingStub(channel);
    }

    public List<IngredientBeerRequest> convertIngredientRestToRequest(List<IngredientDB> ingredientsFromCommand) {
        List<IngredientBeerRequest> ingredientsGRPC = new ArrayList<>();

        for (IngredientDB ingredient : ingredientsFromCommand) {
            IngredientBeerRequest newIngredient = IngredientBeerRequest.newBuilder()
                    .setName(ingredient.getName())
                    .setQuantity(ingredient.getQuantity())
                    .setUnit(ingredient.getUnit())
                    .build();
            ingredientsGRPC.add(newIngredient);
        }
        return ingredientsGRPC;
    }

    // Create Beer
    @PostMapping
    public boolean createBeer(@RequestBody CreateBeerRestModel beer){
            try {
                System.out.println("API CREATE");
                List<IngredientBeerRequest> ingredientsGRPC = convertIngredientRestToRequest(beer.getIngredients());
                CreateBeerRequest request = CreateBeerRequest.newBuilder()
                        .setName(beer.getName())
                        .setDescription(beer.getDescription())
                        .addAllIngredients(ingredientsGRPC)
                        .addAllMethods(Arrays.asList(beer.getMethods()))
                        .setImageUrl(beer.getImageUrl())
                        .setUserId(beer.getUserId())
                        .build();
                beerServiceBlockingStub.createBeerDecomposition(request);

                CreateBeerCommand command = CreateBeerCommand.builder()
                    ._id(UUID.randomUUID().toString())
                    .name(beer.getName())
                    .description(beer.getDescription())
                    .ingredients(beer.getIngredients())
                    .methods(beer.getMethods())
                    .imageUrl(beer.getImageUrl())
                    .userId(beer.getUserId())
                    .build();
                commandGateway.sendAndWait(command);
                return true;
            } catch (Exception e){
                System.out.println(e);
                return  false;
            }
    }
    // Update Beer
    @PutMapping
    public boolean updateBeer(@RequestBody UpdateBeerRestModel beer) {

        try {
            List<IngredientBeerRequest> ingredientsGRPC = convertIngredientRestToRequest(beer.getIngredients());
            UpdateBeerRequest request = UpdateBeerRequest.newBuilder()
                    .setId(beer.get_id())
                    .setName(beer.getName())
                    .setDescription(beer.getDescription())
                    .addAllIngredients(ingredientsGRPC)
                    .addAllMethods(Arrays.asList(beer.getMethods()))
                    .setImageUrl(beer.getImageUrl())
                    .setUserId(beer.getUserId())
                    .build();
            System.out.println(request);
            beerServiceBlockingStub.updateBeerDecomposition(request);

            UpdateBeerCommand command = UpdateBeerCommand.builder()
                    ._id(UUID.randomUUID().toString())
                    .name(beer.getName())
                    .description(beer.getDescription())
                    .ingredients(beer.getIngredients())
                    .methods(beer.getMethods())
                    .imageUrl(beer.getImageUrl())
                    .userId(beer.getUserId())
                    .build();
            commandGateway.sendAndWait(command);

            return true;
        } catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    // Delete Beer
    @DeleteMapping
    public boolean deleteBeer(@RequestBody DeleteBeerRestModel beer) {
        try {
            DeleteBeerRequest request = DeleteBeerRequest.newBuilder()
                    .setBeerId(beer.getBeerId())
                    .setUserId(beer.getUserId())
                    .build();
            beerServiceBlockingStub.deleteBeerDecomposition(request);

            DeleteBeerCommand command = DeleteBeerCommand.builder()
                    ._id(UUID.randomUUID().toString())
                    .beerId(beer.getBeerId())
                    .userId(beer.getUserId())
                    .build();
            commandGateway.sendAndWait(command);

            return true;
        } catch(Exception e){
            System.out.println(e);
            return false;
        }
    }


}