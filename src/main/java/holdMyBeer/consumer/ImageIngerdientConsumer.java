package holdMyBeer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class ImageIngerdientConsumer {
    @RabbitListener(queues = "ImageQueue")
    public String queryImageIngerdient(String name) throws JsonProcessingException {
        System.out.println("Consumer :" +name);
        String imgeInfo = WebClient
                .create()
                .get()
                .uri("https://serpapi.com/search.json?engine=google&q="+"Ingredient "+name+"&google_domain=google.com&gl=us&hl=en&tbm=isch&ijn=1&device=desktop&api_key=bfa1a575995ef617d865c9e71066efa62825feee0fcde9408c973d6824ea932c")
                .retrieve()
                .bodyToMono(String.class)
                .block();
//        System.out.println(imgeInfo);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(imgeInfo);
        JsonNode array = root.get("images_results");
        JsonNode element = array.get(0);
        String thumbnail = element.get("thumbnail").asText();
//        System.out.println(thumbnail);
        return thumbnail;
    }
}
