package holdMyBeer.service;

import com.proto.prime.*;
import holdMyBeer.database.pojo.Beer;
import holdMyBeer.database.pojo.User;
import holdMyBeer.database.pojo.data.IngredientDB;
import holdMyBeer.database.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService extends AuthenticationServiceGrpc.AuthenticationServiceImplBase {
    @Autowired
    private UserRepository userRepository;

    public List<IngredientDB> convertIngredientRequestToDB(List<IngredientUser> ingredientsRequest){
        List<IngredientDB> ingredientsDB = new ArrayList<>();
        for (IngredientUser ingredient : ingredientsRequest) {
            IngredientDB newIngredient = new IngredientDB();
            newIngredient.set_id(ingredient.getId());
            newIngredient.setName(ingredient.getName());
            newIngredient.setQuantity(ingredient.getQuantity());
            newIngredient.setUnit(ingredient.getUnit());
            ingredientsDB.add(newIngredient);
        }

        return ingredientsDB;

    }

    public List<Beer> convertBeerRequestToBeerDB(List<BeerUser> beersRequest){
        List<Beer> beersDB = new ArrayList<>();
        for (BeerUser beerUser : beersRequest) {
            Beer beer = new Beer();
            beer.set_id(beerUser.getId());
            beer.setName(beerUser.getName());
            beer.setDescription(beerUser.getDescription());
            beer.setIngredients(convertIngredientRequestToDB(beerUser.getIngredientsList()));
            beer.setMethods(beerUser.getMethodsList().toArray(new String[0]));
            beersDB.add(beer);
        }

        return beersDB;

    }

    @Override
    public void signInDecomposition(SignInRequest request, StreamObserver<SignInResponse> responseObserver) {
        try {

            User user = new User(convertBeerRequestToBeerDB(request.getFavoriteList()), convertBeerRequestToBeerDB(request.getOwnerList()), request.getFirstName(), request.getLastName(), request.getEmail());
            userRepository.save(user);
            SignInResponse response = SignInResponse.newBuilder()
                    .setIsSuccess(true)
                    .build();
            responseObserver.onNext(response);


        } catch (Exception e){

            SignInResponse response = SignInResponse.newBuilder()
                    .setIsSuccess(false)
                    .build();
            responseObserver.onNext(response);

        }

    }
}
