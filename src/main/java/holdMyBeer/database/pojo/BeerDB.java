package holdMyBeer.database.pojo;
import holdMyBeer.database.pojo.data.IngredientDB;
import lombok.Data;
import org.springframework.data.annotation.Id;
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

    public BeerDB(String _id, String name, String description, List<IngredientDB> ingredients, String[] methods) {
        this._id = _id;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.methods = methods;
    }

    public BeerDB(String name, String description, List<IngredientDB> ingredients, String[] methods) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.methods = methods;
    }

    public BeerDB() {};
}
