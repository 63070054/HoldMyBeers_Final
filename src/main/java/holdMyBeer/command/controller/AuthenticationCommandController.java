package holdMyBeer.command.controller;

import com.proto.prime.*;
import holdMyBeer.command.CreateUserCommand;
import holdMyBeer.command.SignInCommand;
import holdMyBeer.command.rest.SignInRestModel;
import holdMyBeer.database.pojo.BeerDB;
import holdMyBeer.database.pojo.IngredientDB;
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
public class AuthenticationCommandController {
    @Autowired
    private CommandGateway commandGateway;
    private final AuthenticationServiceGrpc.AuthenticationServiceBlockingStub authenticationServiceBlockingStub;

    public AuthenticationCommandController(ManagedChannel channel){
        this.authenticationServiceBlockingStub = AuthenticationServiceGrpc.newBlockingStub(channel);
    }

    public List<IngredientUserRequest> convertIngredientDBToRequest(List<IngredientDB> ingredientsDB){
        List<IngredientUserRequest> ingredientsRequest = new ArrayList<>();
        for (IngredientDB ingredient : ingredientsDB) {
            IngredientUserRequest newIngredient = IngredientUserRequest.newBuilder()
                    .setName(ingredient.getName())
                    .setQuantity(ingredient.getQuantity())
                    .setUnit(ingredient.getUnit())
                    .build();
            ingredientsRequest.add(newIngredient);
        }

        return ingredientsRequest;

    }

    public List<BeerUserRequest> convertBeerDBToRequest(List<BeerDB> beersDB){

        List<BeerUserRequest> beersUser = new ArrayList<>();
        for (BeerDB beerDB : beersDB) {
            BeerUserRequest newBeersUser = BeerUserRequest.newBuilder()
                .setId(beerDB.get_id())
                .setName(beerDB.getName())
                .setDescription(beerDB.getDescription())
                .addAllIngredients(convertIngredientDBToRequest(beerDB.getIngredients()))
                .addAllMethods(Arrays.asList(beerDB.getMethods()))
                .setImageUrl(beerDB.getImageUrl())
                .setUserId(beerDB.getUserId())
                .build();
            beersUser.add(newBeersUser);
        }

        return beersUser;

    }

    @PostMapping("login")
    public boolean signIn(@RequestBody SignInRestModel user) {

        try {
            SignInRequest request = SignInRequest.newBuilder()
                    .setGoogleId(user.getGoogleId())
                    .addAllFavorite(convertBeerDBToRequest(user.getFavorite()))
                    .addAllOwner(convertBeerDBToRequest(user.getOwner()))
                    .setFirstName(user.getFirstName())
                    .setLastName(user.getLastName())
                    .setEmail(user.getEmail())
                    .setImageUrl(user.getImageUrl())
                    .build();
            authenticationServiceBlockingStub.signInDecomposition(request);

            SignInCommand signInCommand = SignInCommand.builder()
                    ._id(UUID.randomUUID().toString())
                    .favorite(user.getFavorite())
                    .owner(user.getOwner())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .imageUrl(user.getImageUrl())
                    .build();
            commandGateway.sendAndWait(signInCommand);


            CheckUserExistRequest checkRequest = CheckUserExistRequest.newBuilder()
                    .setId(user.getGoogleId())
                    .build();
            boolean isUserExist = authenticationServiceBlockingStub.checkUserExists(checkRequest).getIsSuccess();

            if(!isUserExist) {
                authenticationServiceBlockingStub.createUserDecomposition(request);

                CreateUserCommand createUserCommandcommand = CreateUserCommand.builder()
                        ._id(UUID.randomUUID().toString())
                        .favorite(user.getFavorite())
                        .owner(user.getOwner())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .imageUrl(user.getImageUrl())
                        .build();
                commandGateway.sendAndWait(createUserCommandcommand);

            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
