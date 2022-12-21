package holdMyBeer.event;

import lombok.Data;

@Data
public class DeleteBeerEvent {
    private String _id;
    private String beerId;
    private String userId;
}
