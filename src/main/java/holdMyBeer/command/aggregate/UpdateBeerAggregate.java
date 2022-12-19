package holdMyBeer.command.aggregate;

import holdMyBeer.command.UpdateBeerCommand;
import holdMyBeer.event.UpdateBeerEvent;
import holdMyBeer.database.pojo.data.IngredientDB;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Aggregate
public class UpdateBeerAggregate {
    @AggregateIdentifier
    private String _id;
    private String name;
    private String description;
    private List<IngredientDB> ingredients;
    private String[] methods;

    public UpdateBeerAggregate(){

    }
    @CommandHandler
    public UpdateBeerAggregate(UpdateBeerCommand updateBeerCommand){
        System.out.println("Update Beer Command Aggregate");
        UpdateBeerEvent updateBeerEvent = new UpdateBeerEvent();
        BeanUtils.copyProperties(updateBeerCommand, updateBeerEvent);
        AggregateLifecycle.apply(updateBeerEvent);
    }

    @EventSourcingHandler
    public void onUpdateBeerEvent(UpdateBeerEvent updateBeerEvent){
        this._id = updateBeerEvent.get_id();
        this.name = updateBeerEvent.getName();
        this.description = updateBeerEvent.getDescription();
        this.ingredients = updateBeerEvent.getIngredients();
        this.methods = updateBeerEvent.getMethods();
    }
}
