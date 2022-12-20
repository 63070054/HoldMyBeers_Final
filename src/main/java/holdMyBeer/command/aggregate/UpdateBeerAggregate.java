package holdMyBeer.command.aggregate;

import holdMyBeer.command.UpdateBeerCommand;
import holdMyBeer.event.UpdateBeerEvent;
import holdMyBeer.database.pojo.IngredientDB;
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
    public UpdateBeerAggregate(UpdateBeerCommand command){
        UpdateBeerEvent event = new UpdateBeerEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void onUpdateBeerEvent(UpdateBeerEvent event){
        this._id = event.get_id();
        this.name = event.getName();
        this.description = event.getDescription();
        this.ingredients = event.getIngredients();
        this.methods = event.getMethods();
    }
}
