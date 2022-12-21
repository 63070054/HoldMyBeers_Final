package holdMyBeer.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Data
public class DeleteBeerCommand {
    @TargetAggregateIdentifier
    private final String _id;
    private final String beerId;
    private final String userId;

}
