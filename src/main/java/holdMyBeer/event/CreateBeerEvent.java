package holdMyBeer.event;

import holdMyBeer.database.pojo.IngredientDB;
import lombok.Data;
import java.util.List;

@Data
public class CreateBeerEvent {
    private String _id;
    private String name;
    private String description;
    private List<IngredientDB> ingredients;
    private String[] methods;
    private String imageUrl;
    private String userId;
}
