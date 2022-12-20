package holdMyBeer.event;

import lombok.Data;

@Data
public class AddFavoriteBeerEvent {
    private String _id;
    private String userId;
    private String beerId;
}
