package holdMyBeer.query.controller;

import com.proto.prime.*;
import holdMyBeer.database.pojo.BeerDB;
import holdMyBeer.database.pojo.IngredientDB;
import holdMyBeer.database.pojo.UserDB;
import holdMyBeer.database.repository.UserRepository;
import io.grpc.ManagedChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class AuthenticationQueryController {

    private final AuthenticationServiceGrpc.AuthenticationServiceBlockingStub authenticationServiceBlockingStub;

    public AuthenticationQueryController(ManagedChannel channel){
        this.authenticationServiceBlockingStub = AuthenticationServiceGrpc.newBlockingStub(channel);
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

    @GetMapping("{id}")
    public UserDB getUserInfo(@PathVariable String id) {
        try {
            GetUserRequest request = GetUserRequest.newBuilder()
                    .setUserId(id)
                    .build();

            GetUserResponse response = authenticationServiceBlockingStub.getUserInfo(request);
            return new UserDB(response.getGoogleId(), convertBeerUserInfoToDB(response.getFavoritieList()), convertBeerUserInfoToDB(response.getOwnerList()), response.getFirstName(), response.getLastName(), response.getEmail(), response.getImageUrl());
        } catch (Exception e){
            return null;
        }

    }
}
