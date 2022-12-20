package holdMyBeer.command.controller;

import com.proto.prime.AddBeerToFavoriteRequest;
import com.proto.prime.FavoriteBeerServiceGrpc;
import com.proto.prime.RemoveBeerToFavoriteRequest;
import holdMyBeer.command.AddFavoriteBeerCommand;
import holdMyBeer.command.RemoveFavoriteBeerCommand;
import holdMyBeer.command.rest.AddFavoriteRestModel;
import holdMyBeer.command.rest.RemoveFavoriteRestModel;
import io.grpc.ManagedChannel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("favorite")
public class FavoriteCommandController {

    @Autowired
    private CommandGateway commandGateway;
    private final FavoriteBeerServiceGrpc.FavoriteBeerServiceBlockingStub favoriteBeerServiceBlockingStub;

    public FavoriteCommandController(ManagedChannel channel) {
        this.favoriteBeerServiceBlockingStub = FavoriteBeerServiceGrpc.newBlockingStub(channel);
    }


    @PostMapping("/add")
    public boolean addBeerToFavorite(@RequestBody AddFavoriteRestModel model) {
        try {
            AddBeerToFavoriteRequest request = AddBeerToFavoriteRequest.newBuilder()
                    .setBeerId(model.getBeerId())
                    .setUserId(model.getUserId())
                    .build();

            favoriteBeerServiceBlockingStub.addBeerToFavorite(request);

            AddFavoriteBeerCommand command = AddFavoriteBeerCommand.builder()
                    ._id(UUID.randomUUID().toString())
                    .userId(model.getUserId())
                    .beerId(model.getBeerId())
                    .build();
            commandGateway.sendAndWait(command);

            return true;
        } catch(Exception e){
            System.out.println(e);
            return false;
        }
    }

    @DeleteMapping("/remove")
    public boolean addBeerToFavorite(@RequestBody RemoveFavoriteRestModel model) {
        try {
            RemoveBeerToFavoriteRequest request = RemoveBeerToFavoriteRequest.newBuilder()
                    .setBeerId(model.getBeerId())
                    .setUserId(model.getUserId())
                    .build();

            favoriteBeerServiceBlockingStub.removeBeerToFavorite(request);

            RemoveFavoriteBeerCommand command = RemoveFavoriteBeerCommand.builder()
                    ._id(UUID.randomUUID().toString())
                    .userId(model.getUserId())
                    .beerId(model.getBeerId())
                    .build();
            commandGateway.sendAndWait(command);

            return true;
        } catch(Exception e){
            System.out.println(e);
            return false;
        }
    }

}
