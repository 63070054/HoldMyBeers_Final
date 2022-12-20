package holdMyBeer.command.aggregate;

import holdMyBeer.command.SignInCommand;
import holdMyBeer.database.pojo.BeerDB;
import holdMyBeer.event.SignInEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Aggregate
public class SignInAggregate {

    @AggregateIdentifier
    private String _id;
    private List<BeerDB> favorite;
    private List<BeerDB> owner;
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;

    public SignInAggregate() {};

    @CommandHandler
    public SignInAggregate(SignInCommand command){
        SignInEvent event = new SignInEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);

    }

    @EventSourcingHandler
    public void onSignInEvent(SignInEvent event){
        this._id = event.get_id();
        this.favorite = event.getFavorite();
        this.owner = event.getOwner();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.email = event.getEmail();
        this.imageUrl = event.getImageUrl();
    }


}
