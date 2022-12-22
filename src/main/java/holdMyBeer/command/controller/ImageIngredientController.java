package holdMyBeer.command.controller;

import com.fasterxml.jackson.core.JsonProcessingException;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image/ingredient")
public class ImageIngredientController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @GetMapping("/{name}")
    public String queryImageIngerdient(@PathVariable String name) {
        String image = (String) rabbitTemplate.convertSendAndReceive("BeerExchanges", "sentImage", name);
//        System.out.println(image);
        return image;
    }

}
