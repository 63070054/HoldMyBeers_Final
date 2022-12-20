package holdMyBeer.service;

import com.proto.prime.*;
import holdMyBeer.database.pojo.BeerDB;
import holdMyBeer.database.pojo.UserDB;
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

    public List<IngredientDB> convertIngredientRequestToDB(List<IngredientUserRequest> ingredientsRequest){
        List<IngredientDB> ingredientsDB = new ArrayList<>();
        for (IngredientUserRequest ingredient : ingredientsRequest) {
            IngredientDB newIngredient = new IngredientDB();
            newIngredient.set_id(ingredient.getId());
            newIngredient.setName(ingredient.getName());
            newIngredient.setQuantity(ingredient.getQuantity());
            newIngredient.setUnit(ingredient.getUnit());
            ingredientsDB.add(newIngredient);
        }

        return ingredientsDB;

    }

    public List<BeerDB> convertBeerRequestToBeerDB(List<BeerUserRequest> beersRequest){
        List<BeerDB> beersDB = new ArrayList<>();
        for (BeerUserRequest beerUser : beersRequest) {
            BeerDB beerDB = new BeerDB();
            beerDB.set_id(beerUser.getId());
            beerDB.setName(beerUser.getName());
            beerDB.setDescription(beerUser.getDescription());
            beerDB.setIngredients(convertIngredientRequestToDB(beerUser.getIngredientsList()));
            beerDB.setMethods(beerUser.getMethodsList().toArray(new String[0]));
            beersDB.add(beerDB);
        }

        return beersDB;

    }

    @Override
    public void signInDecomposition(SignInRequest request, StreamObserver<SignInResponse> responseObserver) {
        try {

            UserDB userDB = new UserDB(convertBeerRequestToBeerDB(request.getFavoriteList()), convertBeerRequestToBeerDB(request.getOwnerList()), request.getFirstName(), request.getLastName(), request.getEmail());
            userRepository.save(userDB);
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
