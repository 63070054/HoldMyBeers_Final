package holdMyBeer.config;

import com.mongodb.client.MongoClient;
import holdMyBeer.service.AuthenticationService;
import holdMyBeer.service.BeerService;
import holdMyBeer.service.FavoriteBeerService;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class GrpcServerConfig {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private BeerService beerService;
    @Autowired
    private FavoriteBeerService favoriteBeerService;


    @Bean
    public ServerBuilder<?> ServerBuilder(MongoClient mongoClient) {
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(50052);
        serverBuilder.addService(authenticationService);
        serverBuilder.addService(beerService);
        serverBuilder.addService(favoriteBeerService);
        return serverBuilder;
    }

}
