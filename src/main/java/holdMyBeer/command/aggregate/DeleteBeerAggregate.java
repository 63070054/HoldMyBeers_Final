package holdMyBeer.command.aggregate;

import holdMyBeer.command.DeleteBeerCommand;
import holdMyBeer.event.DeleteBeerEvent;
import holdMyBeer.event.UpdateBeerEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class DeleteBeerAggregate {
    @AggregateIdentifier
    private String _id;

    public DeleteBeerAggregate(){

    }
    @CommandHandler
    public DeleteBeerAggregate(DeleteBeerCommand command){
        DeleteBeerEvent event = new DeleteBeerEvent();
        BeanUtils.copyProperties(command, event);
        System.out.println(event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void onDeleteBeerEvent(DeleteBeerEvent event){
        this._id = event.get_id();
    }

}
