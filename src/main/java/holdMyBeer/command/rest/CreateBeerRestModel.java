package holdMyBeer.command.rest;

import com.proto.prime.Ingredient;
import lombok.Data;

import java.util.List;

@Data
public class CreateBeerRestModel {
    private String name;
    private String description;
    private List<Ingredient> ingredients;
    private String[] methods;

}
