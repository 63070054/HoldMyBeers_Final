package holdMyBeer.command;

import holdMyBeer.database.pojo.data.IngredientDB;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Builder
@Data
public class UpdateBeerCommand {
    @TargetAggregateIdentifier
    private final String _id;
    private final String name;
    private final String description;
    private final List<IngredientDB> ingredients;
    private final String[] methods;
}
