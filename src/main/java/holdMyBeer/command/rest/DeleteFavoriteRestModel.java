package holdMyBeer.command.rest;

import lombok.Data;

@Data
public class DeleteFavoriteRestModel {
    private String userId;
    private String beerId;
}
