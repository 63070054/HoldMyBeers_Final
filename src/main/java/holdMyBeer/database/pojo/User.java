package holdMyBeer.database.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Data
@Document("Users")
public class User implements Serializable {
    @Id
    private String _id;
    private List<Beer> favorite;
    private List<Beer> owner;
    private String firstName;
    private String lastName;
    private String email;

    public User(String _id, List<Beer> favorite, List<Beer> owner, String firstName, String lastName, String email){
        this._id = _id;
        this.favorite = favorite;
        this.owner = owner;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(List<Beer> favorite, List<Beer> owner, String firstName, String lastName, String email){
        this.favorite = favorite;
        this.owner = owner;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }



}
