package holdMyBeer.config;

import com.mongodb.client.MongoClient;
import holdMyBeer.service.AuthenticationService;
import holdMyBeer.service.BeerService;
import holdMyBeer.service.FavoriteBeerService;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcServerConfig {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private BeerService beerService;
    @Autowired
    private FavoriteBeerService favoriteBeerService;


    public ServerBuilder<?> ServerBuilder(MongoClient mongoClient) {
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(50052);
        serverBuilder.addService(authenticationService);
        serverBuilder.addService(beerService);
        serverBuilder.addService(favoriteBeerService);
        return serverBuilder;
    }

}
