package holdMyBeer.command.rest;

import lombok.Data;

@Data
public class DeleteBeerRestModel {

    private String beerId;
    private String userId;

}
