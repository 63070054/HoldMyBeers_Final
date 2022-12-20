package holdMyBeer.service;

import com.proto.prime.AuthenticationServiceGrpc;
import com.proto.prime.SignInRequest;
import com.proto.prime.SignInResponse;
import holdMyBeer.database.repository.BeerRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService extends AuthenticationServiceGrpc.AuthenticationServiceImplBase {
    @Autowired
    private BeerRepository beerRepository;

    @Override
    public void signInDecomposition(SignInRequest request, StreamObserver<SignInResponse> responseObserver) {
        try {



        } catch (Exception e){

        }

    }
}
