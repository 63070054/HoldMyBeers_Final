package holdMyBeer.command.aggregate;

import com.proto.prime.Ingredient;
import holdMyBeer.command.controller.BeerCommandController;
import holdMyBeer.database.event.CreateBeerEvent;
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
    private List<Ingredient>  ingredients;
    private String[] methods;

    public CreateBeerAggregate(){

    }
    @CommandHandler
    public CreateBeerAggregate(BeerCommandController beerCommandController){
        CreateBeerEvent createBeerEvent = new CreateBeerEvent();
        BeanUtils.copyProperties(beerCommandController, createBeerEvent);
        AggregateLifecycle.apply(createBeerEvent);
   }

   @EventSourcingHandler
    public void on(CreateBeerEvent createBeerEvent){
       this._id = createBeerEvent.get_id();
       this.name = createBeerEvent.getName();
       this.description = createBeerEvent.getDescription();
       this.ingredients = createBeerEvent.getIngredients();
       this.methods = createBeerEvent.getMethods();
   }
}