package holdMyBeer.command.rest;

import lombok.Data;

@Data
public class DeleteBeerRestModel {

    private String _id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
