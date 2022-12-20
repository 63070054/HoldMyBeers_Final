package holdMyBeer.event;

import lombok.Data;

@Data
public class RemoveFavoriteBeerEvent {
    private String _id;
    private String userId;
    private String beerId;
}
