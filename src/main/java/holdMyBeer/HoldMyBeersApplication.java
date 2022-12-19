package holdMyBeer;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


import java.io.IOException;

@SpringBootApplication
public class  HoldMyBeersApplication {

    public static void main(String[] args) {
       try {
           ConfigurableApplicationContext context = SpringApplication.run(HoldMyBeersApplication.class, args);
           Server server = context.getBean(ServerBuilder.class).build().start();
           try {
               server.awaitTermination();
           } catch (InterruptedException e){
               e.printStackTrace();
           }
       } catch(IOException e){
           e.printStackTrace();
       }

    }

}
