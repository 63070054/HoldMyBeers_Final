package holdMyBeer.command;

import com.proto.prime.Ingredient;
import holdMyBeer.command.controller.BeerCommandController;
import holdMyBeer.database.event.BeerCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Aggregate
public class BeerAggregate {

    @AggregateIdentifier
    private String _id;
    private String name;
    private String description;
    private List<Ingredient>  ingredients;
    private String[] methods;

    public BeerAggregate(){

    }
    @CommandHandler
    public BeerAggregate(BeerCommandController beerCommandController){

        BeerCreatedEvent beerCreatedEvent = new BeerCreatedEvent();
        BeanUtils.copyProperties(beerCommandController, beerCreatedEvent);
        AggregateLifecycle.apply(beerCreatedEvent);
   }

}
