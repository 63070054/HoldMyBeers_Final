package holdMyBeer.command.rest;

import lombok.Data;

@Data
public class RemoveFavoriteRestModel {
    private String userId;
    private String beerId;
}
