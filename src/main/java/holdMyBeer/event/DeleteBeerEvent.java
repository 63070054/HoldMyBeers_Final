package holdMyBeer.event;

import holdMyBeer.database.pojo.data.IngredientDB;
import lombok.Data;

import java.util.List;

@Data
public class DeleteBeerEvent {
    private String _id;
}
