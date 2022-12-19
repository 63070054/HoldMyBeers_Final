package holdMyBeer.database.pojo.data;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
public class IngredientDB implements Serializable {

    @Id
    private String _id;
    private String name;
    private Integer quantity;
    private String unit;

}
