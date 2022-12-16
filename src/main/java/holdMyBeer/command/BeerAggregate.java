package holdMyBeer.command;

import holdMyBeer.command.rest.BeerCommandController;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class BeerAggregate {
    public BeerAggregate(){

    }
    @CommandHandler
    public BeerAggregate(BeerCommandController beerCommandController){
    }
}
