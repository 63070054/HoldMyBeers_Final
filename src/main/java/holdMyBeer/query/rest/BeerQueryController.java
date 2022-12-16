package holdMyBeer.query.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/beers")
public class BeerQueryController {

    @GetMapping
    public String getProducts(){
        return "wavewave";
    }
}
