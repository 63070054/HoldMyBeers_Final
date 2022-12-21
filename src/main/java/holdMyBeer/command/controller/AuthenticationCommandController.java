package holdMyBeer.command.controller;

import com.proto.prime.*;
import holdMyBeer.command.CreateUserCommand;
import holdMyBeer.command.SignInCommand;
import holdMyBeer.command.rest.SignInRestModel;
import holdMyBeer.database.pojo.BeerDB;
import holdMyBeer.database.pojo.IngredientDB;
import holdMyBeer.database.pojo.UserDB;
import holdMyBeer.database.repository.UserRepository;
import io.grpc.ManagedChannel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class AuthenticationCommandController {
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private UserRepository userRepository;
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

    public List<IngredientDB> convertIngredientUserInfoToDB(List<IngredientUserInfo> ingredientsUser){
        List<IngredientDB> ingredientsUserInfo = new ArrayList<>();
        for (IngredientUserInfo ingredient : ingredientsUser) {
            IngredientDB newIngredient = new IngredientDB();
            newIngredient.setName(ingredient.getName());
            newIngredient.setQuantity(ingredient.getQuantity());
            newIngredient.setUnit(ingredient.getUnit());
            ingredientsUserInfo.add(newIngredient);
        }

        return ingredientsUserInfo;

    }

    public List<BeerDB> convertBeerUserInfoToDB(List<BeerUserInfo> beersUserInfo){
        List<BeerDB> beersDB = new ArrayList<>();
        for (BeerUserInfo beerUser : beersUserInfo) {
            BeerDB beer = new BeerDB();
            beer.set_id(beerUser.getId());
            beer.setName(beerUser.getName());
            beer.setDescription(beerUser.getDescription());
            beer.setIngredients(convertIngredientUserInfoToDB(beerUser.getIngredientsList()));
            beer.setMethods(beerUser.getMethodsList().toArray(new String[0]));
            beer.setImageUrl(beerUser.getImageUrl());
            beer.setUserId(beerUser.getUserId());
            beersDB.add(beer);
        }
        return beersDB;
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

    @GetMapping("{id}")
    public UserDB getUserInfo(@PathVariable String id) {
        try {
            GetUserRequest request = GetUserRequest.newBuilder()
                    .setUserId(id)
                    .build();

            GetUserResponse response = authenticationServiceBlockingStub.getUserInfo(request);
            return new UserDB(response.getGoogleId(), convertBeerUserInfoToDB(response.getFavoritieList()), convertBeerUserInfoToDB(response.getOwnerList()), response.getFirstName(), response.getLastName(), response.getEmail(), response.getImageUrl());
        } catch (Exception e){
            System.out.println("ERROR API");
            return null;
        }

    }
}
