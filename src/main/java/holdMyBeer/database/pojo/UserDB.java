package holdMyBeer.database.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Data
@Document("Users")
public class UserDB implements Serializable {
    @Id
    private String _id;
    private List<BeerDB> favorite;
    private List<BeerDB> owner;
    private String firstName;
    private String lastName;
    private String email;

    public UserDB(String _id, List<BeerDB> favorite, List<BeerDB> owner, String firstName, String lastName, String email){
        this._id = _id;
        this.favorite = favorite;
        this.owner = owner;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public UserDB(List<BeerDB> favorite, List<BeerDB> owner, String firstName, String lastName, String email){
        this.favorite = favorite;
        this.owner = owner;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }



}
