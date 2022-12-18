package holdMyBeer.query.rest;

import holdMyBeer.database.data.Ingredient;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Data
public class BeerRestModel {
    private String _id;
    private String name;
    private String description;
    private List<Ingredient> ingredients;
    private String[] methods;
}
