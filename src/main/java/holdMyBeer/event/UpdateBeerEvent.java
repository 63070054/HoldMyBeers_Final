package holdMyBeer.event;

import holdMyBeer.database.pojo.data.IngredientDB;
import lombok.Data;

import java.util.List;

@Data
public class UpdateBeerEvent {
    private String _id;
    private String name;
    private String description;
    private List<IngredientDB> ingredients;
    private String[] methods;
}