package holdMyBeer.command.rest;

import com.proto.prime.Ingredient;
import lombok.Data;

import java.util.List;

@Data
public class UpdateBeerRestModel {
    private String _id;
    private String name;
    private String description;
    private List<Ingredient> ingredients;
    private String[] methods;

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
