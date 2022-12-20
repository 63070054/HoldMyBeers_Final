package holdMyBeer.command.rest;

import holdMyBeer.database.pojo.Beer;
import lombok.Data;
import java.util.List;

@Data
public class SignInRestModel {

    private List<Beer> favorite;
    private List<Beer> owner;
    private String firstName;
    private String lastName;
    private String imageUrl;

}
