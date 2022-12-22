package holdMyBeer.service;

import com.proto.prime.*;
import holdMyBeer.database.pojo.BeerDB;
import holdMyBeer.database.pojo.UserDB;
import holdMyBeer.database.pojo.IngredientDB;
import holdMyBeer.database.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AuthenticationService extends AuthenticationServiceGrpc.AuthenticationServiceImplBase {
    @Autowired
    private UserRepository userRepository;

    public List<IngredientDB> convertIngredientRequestToDB(List<IngredientUserRequest> ingredientsRequest){
        List<IngredientDB> ingredientsDB = new ArrayList<>();
        for (IngredientUserRequest ingredient : ingredientsRequest) {
            IngredientDB newIngredient = new IngredientDB();
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
            beerDB.setImageUrl(beerUser.getImageUrl());
            beerDB.setUserId(beerUser.getUserId());
            beersDB.add(beerDB);
        }
        return beersDB;
    }

    public List<IngredientUserInfo> convertIngredientDBToUserInfo(List<IngredientDB> ingerdientsDB){
        List<IngredientUserInfo> ingredientsUserInfo = new ArrayList<>();
        for (IngredientDB ingredient : ingerdientsDB) {
            IngredientUserInfo newIngredient = IngredientUserInfo.newBuilder()
                    .setName(ingredient.getName())
                    .setQuantity(ingredient.getQuantity())
                    .setUnit(ingredient.getUnit())
                    .build();
            ingredientsUserInfo.add(newIngredient);
        }

        return ingredientsUserInfo;

    }

    public List<BeerUserInfo> convertBeerDBToUserInfo(List<BeerDB> beersDB){
        List<BeerUserInfo> beersUserInfo = new ArrayList<>();
        for (BeerDB beerDB : beersDB) {
            BeerUserInfo beer = BeerUserInfo.newBuilder()
                    .setId(beerDB.get_id())
                    .setName(beerDB.getName())
                    .setDescription(beerDB.getDescription())
                    .addAllIngredients(convertIngredientDBToUserInfo(beerDB.getIngredients()))
                    .addAllMethods(Arrays.asList(beerDB.getMethods()))
                    .setImageUrl(beerDB.getImageUrl())
                    .setUserId(beerDB.getUserId())
                    .build();
            beersUserInfo.add(beer);
        }
        return beersUserInfo;
    }

    public boolean userExists(String _id){
        return userRepository.existsById(_id);
    }

    @Override
    public void signInDecomposition(SignInRequest request, StreamObserver<SignInResponse> responseObserver) {
        try {
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

        responseObserver.onCompleted();

    }


    @Override
    public void createUserDecomposition(SignInRequest request, StreamObserver<SignInResponse> responseObserver) {
        try {
            UserDB userDB = new UserDB(request.getGoogleId(), convertBeerRequestToBeerDB(request.getFavoriteList()), convertBeerRequestToBeerDB(request.getOwnerList()), request.getFirstName(), request.getLastName(), request.getEmail(), request.getImageUrl());
            System.out.println(userDB);
            userRepository.insert(userDB);

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
        responseObserver.onCompleted();
    }

    @Override
    public void checkUserExists(CheckUserExistRequest request, StreamObserver<CheckUserExistResponse> responseObserver) {
        try {
            CheckUserExistResponse response = CheckUserExistResponse.newBuilder()
                    .setIsSuccess(userExists(request.getId()))
                    .build();

            responseObserver.onNext(response);

        } catch(Exception e){
            CheckUserExistResponse response = CheckUserExistResponse.newBuilder()
                    .setIsSuccess(false)
                    .build();

            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void getUserInfo(GetUserRequest request, StreamObserver<GetUserResponse> responseObserver) {
        try {

            String userId = request.getUserId();
            UserDB userDB = userRepository.findUserById(userId);
            System.out.println(userId);
            System.out.println(userDB);

            GetUserResponse response = GetUserResponse.newBuilder()
                    .setGoogleId(userDB.getGoogleId())
                    .addAllFavoritie(convertBeerDBToUserInfo(userDB.getFavorite()))
                    .addAllOwner(convertBeerDBToUserInfo(userDB.getOwner()))
                    .setFirstName(userDB.getFirstName())
                    .setLastName(userDB.getLastName())
                    .setEmail(userDB.getEmail())
                    .setImageUrl(userDB.getImageUrl())
                    .build();

            responseObserver.onNext(response);

        } catch (Exception e){
            System.out.println("GRPC ERROR");
            GetUserResponse response = GetUserResponse.newBuilder()
                    .setGoogleId("")
                    .addAllFavoritie(null)
                    .addAllOwner(null)
                    .setFirstName("")
                    .setLastName("")
                    .setEmail("")
                    .setImageUrl("")
                    .build();

            responseObserver.onNext(response);

        }
        responseObserver.onCompleted();
    }
}
