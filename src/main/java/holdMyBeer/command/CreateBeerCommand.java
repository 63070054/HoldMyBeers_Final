package holdMyBeer.command;

import holdMyBeer.database.data.Ingredient;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Builder
@Data
public class CreateBeerCommand {
    @TargetAggregateIdentifier
    private String _id;
    private String name;
    private String description;
    private List<Ingredient> ingredients;
    private String[] methods;
}
