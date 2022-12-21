package holdMyBeer.command.rest;

import holdMyBeer.database.pojo.IngredientDB;
import lombok.Data;

import java.util.List;

@Data
public class UpdateBeerRestModel {
    private String _id;
    private String name;
    private String description;
    private List<IngredientDB> ingredients;
    private String[] methods;
    private String imageUrl;
}
