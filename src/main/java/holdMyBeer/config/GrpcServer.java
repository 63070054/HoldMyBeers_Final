package holdMyBeer.config;

import com.mongodb.client.MongoClient;
import holdMyBeer.service.BeerService;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcServer {

    @Autowired
    private BeerService beerService;

    @Bean
    public ServerBuilder<?> serverBuilder(MongoClient mongoClient) {
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(50052);
        serverBuilder.addService(beerService);
        return serverBuilder;
    }

}
