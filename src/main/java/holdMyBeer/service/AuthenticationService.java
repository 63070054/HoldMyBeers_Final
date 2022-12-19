package holdMyBeer.service;

import com.proto.prime.AuthenticationServiceGrpc;
import holdMyBeer.database.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService extends AuthenticationServiceGrpc.AuthenticationServiceImplBase {
    @Autowired
    private BeerRepository beerRepository;
}
