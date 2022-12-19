package holdMyBeer.event.eventHandler;

import com.proto.prime.BeerServiceGrpc;
import com.proto.prime.CreateBeerRequest;
import com.proto.prime.Ingredient;
import holdMyBeer.command.CreateBeerCommand;
import holdMyBeer.command.aggregate.CreateBeerAggregate;
import holdMyBeer.database.pojo.data.IngredientDB;
import holdMyBeer.event.CreateBeerEvent;
import io.grpc.ManagedChannel;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.GenericEventMessage;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.Repository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @EventHandler
    public void handleCreateBeerCommand(CreateBeerEvent event){

        System.out.println("Event Create Trigger : " + event.getName());

        try {
            List<Ingredient> ingredientsGRPC = convertIngredientCommandToRequest(event.getIngredients());
            CreateBeerRequest request = CreateBeerRequest.newBuilder()
                    .setName(event.getName())
                    .setDescription(event.getDescription())
                    .addAllIngredients(ingredientsGRPC)
                    .addAllMethods(Arrays.asList(event.getMethods()))
                    .build();
            beerServiceBlockingStub.createBeerDecomposition(request);
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
