package holdMyBeer.database.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Data
@Document
public class User implements Serializable {
    @Id
    private String _id;
    private String[] favorite;
    private List<Beer> owner;
    private String fname;
    private String lname;
    private String email;
}
