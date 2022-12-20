package holdMyBeer.command.aggregate;

import holdMyBeer.command.AddFavoriteBeerCommand;
import holdMyBeer.event.AddFavoriteBeerEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class AddFavoriteBeerAggregate {

    @AggregateIdentifier
    private String _id;
    private String userId;
    private String beerId;

    public AddFavoriteBeerAggregate(){

    }
    @CommandHandler
    public AddFavoriteBeerAggregate(AddFavoriteBeerCommand command){
        AddFavoriteBeerEvent event = new AddFavoriteBeerEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);

    }

    @EventSourcingHandler
    public void onAddFavoriteBeerEvent(AddFavoriteBeerEvent event){
        this._id = event.get_id();
        this.userId = event.getUserId();
        this.beerId = event.getBeerId();
    }
}
