package holdMyBeer.command;

import com.proto.prime.Ingredient;
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
    private final List<Ingredient> ingredients;
    private final String[] methods;

}
