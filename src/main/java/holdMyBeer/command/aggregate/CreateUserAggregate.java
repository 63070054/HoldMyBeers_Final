package holdMyBeer.command.aggregate;

import holdMyBeer.command.CreateUserCommand;
import holdMyBeer.database.pojo.BeerDB;
import holdMyBeer.event.CreateUserEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Aggregate
public class CreateUserAggregate {

    @AggregateIdentifier
    private String _id;
    private List<BeerDB> favorite;
    private List<BeerDB> owner;
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;

    public CreateUserAggregate() {};

    @CommandHandler
    public CreateUserAggregate(CreateUserCommand command){
        CreateUserEvent event = new CreateUserEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);

    }

    @EventSourcingHandler
    public void onCreateUserEvent(CreateUserEvent event){
        this._id = event.get_id();
        this.favorite = event.getFavorite();
        this.owner = event.getOwner();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.email = event.getEmail();
        this.imageUrl = event.getImageUrl();
    }

}
