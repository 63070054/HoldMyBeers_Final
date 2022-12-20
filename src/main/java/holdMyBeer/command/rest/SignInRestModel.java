package holdMyBeer.command.rest;

import holdMyBeer.database.pojo.Beer;
import lombok.Data;

@Data
public class SignInRestModel {

    private Beer[] favorite;
    private Beer[] owner;
    private String firstName;
    private String lastName;
    private String imageUrl;

}
