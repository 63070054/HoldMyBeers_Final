package holdMyBeer.service;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.bson.json.JsonObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Transactional
@Retryable(include = Exception.class)
@NoAutoStart
public class ImageIngredientService {
    @RabbitListener(queues = "ImageQueue")
    public String queryImageIngerdient(String name) throws JsonProcessingException {
        System.out.println("Consumer :" +name);
        String imgeInfo = WebClient
                .create()
                .get()
                .uri("https://serpapi.com/search.json?engine=google&q="+"Ingredient "+name+"&image_count=1&limit=1&google_domain=google.com&gl=us&hl=en&tbm=isch&ijn=1&device=desktop&api_key=4b0515234215188e89ea22071806e08c9f8a14253c9ac8b096f63d3c7923a629")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(imgeInfo);
        if(root.get("images_results") == null) {
            return "https://semantic-ui.com/images/wireframe/image.png";
        } else {
            JsonNode array = root.get("images_results");
            JsonNode element = array.get(0);
            String thumbnail = element.get("thumbnail").asText();
            return thumbnail;
        }
    }
}
