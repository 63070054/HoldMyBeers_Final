package holdMyBeer.service;

import com.proto.prime.*;
import holdMyBeer.database.pojo.BeerDB;
import holdMyBeer.database.pojo.UserDB;
import holdMyBeer.database.repository.BeerRepository;
import holdMyBeer.database.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteBeerService extends FavoriteBeerServiceGrpc.FavoriteBeerServiceImplBase {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BeerRepository beerRepository;

    @Override
    public void addBeerToFavorite(AddBeerToFavoriteRequest request, StreamObserver<AddBeerToFavoriteResponse> responseObserver) {
        try {
            UserDB selectUserDB = userRepository.findUserById(request.getUserId());
            BeerDB selectBeerDB = beerRepository.findBeerByID(request.getBeerId());
            selectUserDB.getFavorite().add(selectBeerDB);
            userRepository.save(selectUserDB);

            AddBeerToFavoriteResponse response = AddBeerToFavoriteResponse.newBuilder()
                    .setIsSuccess(true)
                    .build();

            responseObserver.onNext(response);

        } catch (Exception e){
            AddBeerToFavoriteResponse response = AddBeerToFavoriteResponse.newBuilder()
                    .setIsSuccess(false)
                    .build();

            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void removeBeerToFavorite(RemoveBeerToFavoriteRequest request, StreamObserver<RemoveBeerToFavoriteResponse> responseObserver) {
        try {
            UserDB selectUserDB = userRepository.findUserById(request.getUserId());
            selectUserDB.getFavorite().removeIf(beer -> beer.get_id().equals(request.getBeerId()));
            userRepository.save(selectUserDB);
            RemoveBeerToFavoriteResponse response = RemoveBeerToFavoriteResponse.newBuilder()
                    .setIsSuccess(true)
                    .build();

            responseObserver.onNext(response);

        } catch (Exception e){
            RemoveBeerToFavoriteResponse response = RemoveBeerToFavoriteResponse.newBuilder()
                    .setIsSuccess(false)
                    .build();

            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }
}
