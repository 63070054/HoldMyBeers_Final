package holdMyBeer.command.controller;

import holdMyBeer.command.CreateBeerCommand;
import holdMyBeer.command.UpdateBeerCommand;
import holdMyBeer.command.rest.CreateBeerRestModel;
import holdMyBeer.command.rest.UpdateBeerRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/beers")
public class BeerCommandController {

    @Autowired
    private CommandGateway commandGateway;


    // Create Beer
    @PostMapping
    public boolean createBeer(@RequestBody CreateBeerRestModel beer){

        System.out.println("API");

            CreateBeerCommand command = CreateBeerCommand.builder()
                    ._id(UUID.randomUUID().toString())
                    .name(beer.getName())
                    .description(beer.getDescription())
                    .ingredients(beer.getIngredients())
                    .methods(beer.getMethods())
                    .build();
            try {
                commandGateway.sendAndWait(command);
                return true;
            } catch (Exception e){
                System.out.println(e);
                return  false;
            }
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
//    @DeleteMapping()
//    public boolean deleteBeer(@RequestBody DeleteBeerRestModel beer) {
//        DeleteBeerRequest request = DeleteBeerRequest.newBuilder()
//                .setId(beer.get_id())
//                .build();
//        return beerServiceBlockingStub.deleteBeerDecomposition(request).getIsSuccess();
//    }


}