package holdMyBeer.command.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image/ingerdient")
public class ImageIngredientController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @GetMapping("/{name}")
    public String queryImageIngerdient(@PathVariable String name) {
        String imageURL = (String) rabbitTemplate.convertSendAndReceive("BeerExchanges", "sentImage", name);
        return imageURL;
    }

}
