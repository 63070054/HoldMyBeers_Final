package holdMyBeer.command.rest;


import holdMyBeer.database.pojo.IngredientDB;
import lombok.Data;

import java.util.List;

@Data
public class CreateBeerRestModel {
    private String name;
    private String description;
    private List<IngredientDB> ingredients;
    private String[] methods;
    private String imageUrl;

}
