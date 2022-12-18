package holdMyBeer.service;

import com.proto.prime.SignInRequest;
import com.proto.prime.SignInResponse;
import holdMyBeer.database.repository.BeerRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthenticationService {
    @Autowired
    private BeerRepository beerRepository;
    @Override
    public void signInDecomposition(SignInRequest request, StreamObserver<SignInResponse> responseObserver) {

    }
}
