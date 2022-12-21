package holdMyBeer.consumer;

import org.bson.json.JsonObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;



import java.util.HashMap;
import java.util.Map;

public class ImageIngerdientConsumer {
    @RabbitListener(queues = "ImageQueue")
    public String queryImageIngerdient(String name) throws SerpApiSearchException{
        Map<String, String> parameter = new HashMap<>();

        parameter.put("api_key", "bfa1a575995ef617d865c9e71066efa62825feee0fcde9408c973d6824ea932c");
        parameter.put("device", "desktop");
        parameter.put("engine", "google");
        parameter.put("q", "Coffee");
        parameter.put("location", "Austin, Texas, United States");
        parameter.put("google_domain", "google.com");
        parameter.put("gl", "us");
        parameter.put("hl", "en");
        parameter.put("tbm", "isch");
        parameter.put("ijn", "1");

        GoogleSearch search = new GoogleSearch(parameter);

        try
        {
            JsonObject results = search.getJson();
        }
        catch (SerpApiClientException ex)
        {
            Console.WriteLine("Exception:");
            Console.WriteLine(ex.ToString());
        }
    }
}
