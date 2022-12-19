package holdMyBeer.command.rest;

import lombok.Data;

@Data
public class AddFavoriteRestModel {
    private String userId;
    private String beerId;
}
