package holdMyBeer.command.controller;

import com.proto.prime.*;
import holdMyBeer.command.DeleteBeerCommand;
import holdMyBeer.command.rest.SignInRestModel;
import holdMyBeer.database.pojo.BeerDB;
import holdMyBeer.database.pojo.data.IngredientDB;
import io.grpc.ManagedChannel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserCommandController {
    @Autowired
    private CommandGateway commandGateway;
    private final AuthenticationServiceGrpc.AuthenticationServiceBlockingStub authenticationServiceBlockingStub;

    public UserCommandController(ManagedChannel channel){
        this.authenticationServiceBlockingStub = AuthenticationServiceGrpc.newBlockingStub(channel);
    }

    public List<IngredientUserRequest> convertIngredientDBToRequest(List<IngredientDB> ingredientsDB){
        List<IngredientUserRequest> ingredientsRequest = new ArrayList<>();
        for (IngredientDB ingredient : ingredientsDB) {
            IngredientUserRequest newIngredient = IngredientUserRequest.newBuilder()
                    .setId(ingredient.get_id())
                    .setName(ingredient.getName())
                    .setQuantity(ingredient.getQuantity())
                    .setUnit(ingredient.getUnit())
                    .build();
            ingredientsRequest.add(newIngredient);
        }

        return ingredientsRequest;

    }

//    public List<Ingredient> convertIngredientRestToRequest(List<IngredientDB> ingredientsFromCommand) {
//        List<Ingredient> ingredientsGRPC = new ArrayList<>();
//
//        for (IngredientDB ingredient : ingredientsFromCommand) {
//            Ingredient newIngredient = Ingredient.newBuilder()
//                    .setName(ingredient.getName())
//                    .setQuantity(ingredient.getQuantity())
//                    .setUnit(ingredient.getUnit())
//                    .build();
//            ingredientsGRPC.add(newIngredient);
//        }
//        return ingredientsGRPC;
//    }

    public List<BeerUserRequest> convertBeerDBToRequest(List<BeerDB> beersDB){
        List<BeerUserRequest> beersUser = new ArrayList<>();
        for (BeerDB beerDB : beersDB) {
            BeerUserRequest newBeersUser = BeerUserRequest.newBuilder()
                .setId(beerDB.get_id())
                .setName(beerDB.getName())
                .setDescription(beerDB.getDescription())
                .addAllIngredients(convertIngredientDBToRequest(beerDB.getIngredients()))
                .addAllMethods(Arrays.asList(beerDB.getMethods()))
                    .build();
            beersUser.add(newBeersUser);
        }

        return beersUser;

    }

    @PostMapping
    public boolean signIn(@RequestBody SignInRestModel user) {
        try {

            SignInRequest request = SignInRequest.newBuilder()
                    .addAllFavorite(convertBeerDBToRequest(user.getFavorite()))
                    .addAllOwner(convertBeerDBToRequest(user.getOwner()))
                    .setFirstName(user.getFirstName())
                    .setLastName(user.getLastName())
                    .setEmail(user.getEmail())
                    .setImageUrl(user.getImageUrl())
                    .build();
            authenticationServiceBlockingStub.signInDecomposition(request);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
