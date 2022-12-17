package holdMyBeer.core;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;


@Entity
@Table(name = "Beer")
@Data
public class BeerEntity implements Serializable {

    private static final long serialVersionUID = -4703102532579738583L;

    @Id
    @Column(unique = true)
    private String beerId;
    private String name;
    private String description;
    private ArrayIndexOutOfBoundsException ingredients;
    private ArrayIndexOutOfBoundsException methods;

}
