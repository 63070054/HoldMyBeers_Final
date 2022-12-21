package holdMyBeer.command.controller;

import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/image/ingerdient")
public class ImageIngredientController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @GetMapping("/{name}")
    public Map<String, Object> queryImageIngerdient(@PathVariable String name) {
        String imgeInfo = WebClient
                .create()
                .get()
                .uri("https://serpapi.com/search.json?engine=google&q="+"Ingredient "+name+"&google_domain=google.com&gl=us&hl=en&tbm=isch&ijn=1&device=desktop&api_key=bfa1a575995ef617d865c9e71066efa62825feee0fcde9408c973d6824ea932c")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(imgeInfo);
        Gson gson = new Gson();
        Map<String, Object> data = gson.fromJson(imgeInfo, Map.class);
//        String imageURL = (String) rabbitTemplate.convertSendAndReceive("BeerExchanges", "sentImage", name);
        String text = (String) data.get("images_results");
        Gson gson2 = new Gson();
        Map<String, List> data2 = (Map<String, List>) gson2.fromJson(text, List.class);
        System.out.println(data2);
        return data;
    }

}
