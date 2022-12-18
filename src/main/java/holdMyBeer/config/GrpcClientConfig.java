package holdMyBeer.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @Bean
    public ManagedChannel channel() {
        return ManagedChannelBuilder.forAddress("localhost", 50052)
                .usePlaintext()
                .build();
    }

}
