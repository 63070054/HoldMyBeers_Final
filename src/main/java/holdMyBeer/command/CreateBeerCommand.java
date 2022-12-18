package holdMyBeer.command;

import com.proto.prime.Ingredient;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Builder
@Data
public class CreateBeerCommand {
    @TargetAggregateIdentifier
    private String _id;
    private String name;
    private String description;
    private List<Ingredient> ingredients;
    private String[] methods;

    public CreateBeerCommand() {
    }

    public CreateBeerCommand(String _id, String name, String description, List<Ingredient> ingredients, String[] methods) {
        this._id = _id;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.methods = methods;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getMethods() {
        return methods;
    }

    public void setMethods(String[] methods) {
        this.methods = methods;
    }
}
