package holdMyBeer.consumer;

import com.google.gson.Gson;
import org.bson.json.JsonObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.web.reactive.function.client.WebClient;



public class ImageIngerdientConsumer {
    @RabbitListener(queues = "ImageQueue")
    public void queryImageIngerdient(String name) {
        String imgeInfo = WebClient
                .create()
                .get()
                .uri("https://serpapi.com/search.json?engine=google&q="+"Ingredient "+name+"&location=Austin%2C+Texas%2C+United+States&google_domain=google.com&gl=us&hl=en&tbm=isch&ijn=1&device=desktop&api_key=bfa1a575995ef617d865c9e71066efa62825feee0fcde9408c973d6824ea932c")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(imgeInfo);

        //        Gson gson = new Gson();
//        Object obj = gson.fromJson(imgeInfo, Object.class);


    }
}
