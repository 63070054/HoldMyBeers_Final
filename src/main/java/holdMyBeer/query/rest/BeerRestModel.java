package holdMyBeer.query.rest;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class BeerRestModel {
    @TargetAggregateIdentifier
    private String BeerId;
    private String name;
    private String description;
    private ArrayIndexOutOfBoundsException ingredients;
    private ArrayIndexOutOfBoundsException methods;
}
