package holdMyBeer.service;

import com.proto.prime.*;
import holdMyBeer.database.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteBeerService extends FavoriteBeerServiceGrpc.FavoriteBeerServiceImplBase {
    @Autowired
    private BeerRepository beerRepository;

}
