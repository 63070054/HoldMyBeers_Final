package holdMyBeer.service;

import com.proto.prime.AddBeerToFavoriteRequest;
import com.proto.prime.AddBeerToFavoriteResponse;
import com.proto.prime.RemoveBeerToFavoriteRequest;
import com.proto.prime.RemoveBeerToFavoriteResponse;
import holdMyBeer.database.repository.BeerRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;

public class FavoriteBeerService {
    @Autowired
    private BeerRepository beerRepository;

}
