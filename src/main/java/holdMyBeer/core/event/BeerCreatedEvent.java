package holdMyBeer.core.event;

import lombok.Data;

@Data
public class BeerCreatedEvent {
    private String BeerId;
    private String name;
    private String description;
    private ArrayIndexOutOfBoundsException ingredients;
    private ArrayIndexOutOfBoundsException methods;

}
