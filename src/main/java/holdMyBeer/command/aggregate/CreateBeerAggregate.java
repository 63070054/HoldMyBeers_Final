package holdMyBeer.command.aggregate;

import holdMyBeer.command.CreateBeerCommand;
import holdMyBeer.event.CreateBeerEvent;
import holdMyBeer.database.pojo.IngredientDB;
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
    public CreateBeerAggregate(CreateBeerCommand command){
        CreateBeerEvent event = new CreateBeerEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);

   }

   @EventSourcingHandler
    public void onCreateBeerEvent(CreateBeerEvent event){
       this._id = event.get_id();
       this.name = event.getName();
       this.description = event.getDescription();
       this.ingredients = event.getIngredients();
       this.methods = event.getMethods();
   }
}
