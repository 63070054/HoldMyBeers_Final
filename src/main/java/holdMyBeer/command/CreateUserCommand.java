package holdMyBeer.command;

import holdMyBeer.database.pojo.BeerDB;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Builder
@Data
public class CreateUserCommand {

    @TargetAggregateIdentifier
    private String _id;
    private List<BeerDB> favorite;
    private List<BeerDB> owner;
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;

}
