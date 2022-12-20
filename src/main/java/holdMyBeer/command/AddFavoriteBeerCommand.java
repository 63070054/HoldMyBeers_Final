package holdMyBeer.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Data
public class AddFavoriteBeerCommand {
    @TargetAggregateIdentifier
    private final String _id;
    private final String userId;
    private final String beerId;
}
