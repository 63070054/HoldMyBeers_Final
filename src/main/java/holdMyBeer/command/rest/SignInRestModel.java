package holdMyBeer.command.rest;

import holdMyBeer.database.pojo.BeerDB;
import lombok.Data;
import java.util.List;

@Data
public class SignInRestModel {

    private String googleId;
    private List<BeerDB> favorite;
    private List<BeerDB> owner;
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;

}
