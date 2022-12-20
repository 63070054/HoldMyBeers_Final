package holdMyBeer.event;

import holdMyBeer.database.pojo.BeerDB;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserEvent {

    private String _id;
    private List<BeerDB> favorite;
    private List<BeerDB> owner;
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;

}
