package holdMyBeer.command.aggregate;

import holdMyBeer.command.RemoveFavoriteBeerCommand;
import holdMyBeer.event.RemoveFavoriteBeerEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class RemoveFavoriteBeerAggregate {
    @AggregateIdentifier
    private String _id;
    private String userId;
    private String beerId;

    public RemoveFavoriteBeerAggregate(){

    }
    @CommandHandler
    public RemoveFavoriteBeerAggregate(RemoveFavoriteBeerCommand command){
        RemoveFavoriteBeerEvent event = new RemoveFavoriteBeerEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);

    }

    @EventSourcingHandler
    public void onRemoveFavoriteBeerEvent(RemoveFavoriteBeerEvent event){
        this._id = event.get_id();
        this.userId = event.getUserId();
        this.beerId = event.getBeerId();
    }
}
