package holdMyBeer.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Data
public class CreateBeerCommand {
    @TargetAggregateIdentifier
    private final String beerId;
    private final String name;
    private final String description;
    private ArrayIndexOutOfBoundsException ingredients;
    private ArrayIndexOutOfBoundsException methods;
}
