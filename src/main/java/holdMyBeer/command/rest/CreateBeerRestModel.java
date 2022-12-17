package holdMyBeer.command.rest;

import holdMyBeer.database.data.Ingredient;
import lombok.Data;

import java.util.List;

@Data
public class CreateBeerRestModel {
    private String _id;
    private String name;
    private String description;
    private List<Ingredient> ingredients;
    private String[] methods;
}
