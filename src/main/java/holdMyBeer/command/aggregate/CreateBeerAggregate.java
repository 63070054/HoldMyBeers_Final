package holdMyBeer.command.aggregate;

import holdMyBeer.command.CreateBeerCommand;
import holdMyBeer.event.CreateBeerEvent;
import holdMyBeer.database.pojo.data.IngredientDB;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Aggregate
public class CreateBeerAggregate {

    @AggregateIdentifier
    private String _id;
    private String name;
    private String description;
    private List<IngredientDB>  ingredients;
    private String[] methods;

    public CreateBeerAggregate(){

    }
    @CommandHandler
    public CreateBeerAggregate(CreateBeerCommand createBeerCommand){

        System.out.println("Aggregate");

        CreateBeerEvent createBeerEvent = new CreateBeerEvent();
        BeanUtils.copyProperties(createBeerCommand, createBeerEvent);
        AggregateLifecycle.apply(createBeerEvent);

   }

   @EventSourcingHandler
    public void onCreateBeerEvent(CreateBeerEvent createBeerEvent){

       this._id = createBeerEvent.get_id();
       this.name = createBeerEvent.getName();
       this.description = createBeerEvent.getDescription();
       this.ingredients = createBeerEvent.getIngredients();
       this.methods = createBeerEvent.getMethods();
   }
}
