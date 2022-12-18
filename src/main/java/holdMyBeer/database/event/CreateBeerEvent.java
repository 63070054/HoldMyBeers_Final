package holdMyBeer.database.event;

import com.proto.prime.Ingredient;
import lombok.Data;
import java.util.List;

@Data
public class CreateBeerEvent {
    private String _id;
    private String name;
    private String description;
    private List<Ingredient> ingredients;
    private String[] methods;


}
