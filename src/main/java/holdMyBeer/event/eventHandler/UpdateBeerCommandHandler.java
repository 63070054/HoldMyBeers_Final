package holdMyBeer.event.eventHandler;

import com.proto.prime.*;
import holdMyBeer.command.CreateBeerCommand;
import holdMyBeer.command.UpdateBeerCommand;
import holdMyBeer.database.pojo.data.IngredientDB;
import holdMyBeer.event.CreateBeerEvent;
import holdMyBeer.event.UpdateBeerEvent;
import io.grpc.ManagedChannel;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UpdateBeerCommandHandler {
    private final BeerServiceGrpc.BeerServiceBlockingStub beerServiceBlockingStub;

    public UpdateBeerCommandHandler(ManagedChannel channel) {
        this.beerServiceBlockingStub = BeerServiceGrpc.newBlockingStub(channel);
    }

    public List<Ingredient> convertIngredientCommandToRequest(List<IngredientDB> ingredientsFromCommand) {
        List<Ingredient> ingredientsGRPC = new ArrayList<>();

        for (IngredientDB ingredient : ingredientsFromCommand) {
            Ingredient newIngredient = Ingredient.newBuilder()
                    .setName(ingredient.getName())
                    .setQuantity(ingredient.getQuantity())
                    .setUnit(ingredient.getUnit())
                    .build();
            ingredientsGRPC.add(newIngredient);
        }
        return ingredientsGRPC;
    }

    @EventHandler
    public void handleUpdateBeerEvent(UpdateBeerEvent event){

        System.out.println("Event Update Trigger");

        List<Ingredient> ingredientsGRPC = convertIngredientCommandToRequest(event.getIngredients());

        UpdateBeerRequest request = UpdateBeerRequest.newBuilder()
                .setId(event.get_id())
                .setName(event.getName())
                .setDescription(event.getDescription())
                .addAllIngredients(ingredientsGRPC)
                .addAllMethods(Arrays.asList(event.getMethods()))
                .build();
        beerServiceBlockingStub.updateBeerDecomposition(request).getIsSuccess();

    }
}
