package holdMyBeer.event.eventHandler;

import com.proto.prime.BeerServiceGrpc;
import com.proto.prime.CreateBeerRequest;
import com.proto.prime.Ingredient;
import holdMyBeer.command.CreateBeerCommand;
import holdMyBeer.database.pojo.data.IngredientDB;
import io.grpc.ManagedChannel;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CreateBeerCommandHandler {

    private final BeerServiceGrpc.BeerServiceBlockingStub beerServiceBlockingStub;

    public CreateBeerCommandHandler(ManagedChannel channel) {
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

    @CommandHandler
    public boolean handleCreateBeerEvent(CreateBeerCommand command){

        System.out.println("Handle");

        List<Ingredient> ingredientsGRPC = convertIngredientCommandToRequest(command.getIngredients());

        CreateBeerRequest request = CreateBeerRequest.newBuilder()
                .setName(command.getName())
                .setDescription(command.getDescription())
                .addAllIngredients(ingredientsGRPC)
                .addAllMethods(Arrays.asList(command.getMethods()))
                .build();
        return beerServiceBlockingStub.createBeerDecomposition(request).getIsSuccess();

    }
}
