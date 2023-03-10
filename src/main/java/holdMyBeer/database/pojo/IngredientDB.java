package holdMyBeer.database.pojo;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import java.io.Serializable;

@Data
public class IngredientDB implements Serializable {

    private String name;
    private Integer quantity;
    private String unit;

}
