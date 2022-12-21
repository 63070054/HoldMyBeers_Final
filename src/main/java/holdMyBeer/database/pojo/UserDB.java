package holdMyBeer.database.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

@Data
@Document("Users")
public class UserDB implements Serializable {

    @Id
    @Field("googleId")
    private String googleId;
    private List<BeerDB> favorite;
    private List<BeerDB> owner;
    private String firstName;
    private String lastName;
    private String email;
    private String userId;

    public UserDB(String googleId, List<BeerDB> favorite, List<BeerDB> owner, String firstName, String lastName, String email, String userId){
        this.googleId = googleId;
        this.favorite = favorite;
        this.owner = owner;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userId = userId;
    }



}
