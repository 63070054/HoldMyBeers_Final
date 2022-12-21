package holdMyBeer.database.pojo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;


@Data
@Document("Beers")
public class BeerDB implements Serializable {

    @Id
    private String _id;
    private String name;
    private String description;
    private List<IngredientDB> ingredients;
    private String[] methods;
    private String imageUrl;
    private String userId;

    public BeerDB(String _id, String name, String description, List<IngredientDB> ingredients, String[] methods, String imageUrl, String userId) {
        this._id = _id;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.methods = methods;
        this.imageUrl = imageUrl;
        this.userId = userId;
    }

    public BeerDB(String name, String description, List<IngredientDB> ingredients, String[] methods, String imageUrl, String userId) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.methods = methods;
        this.imageUrl = imageUrl;
        this.userId = userId;
    }

    public BeerDB() {};
}
