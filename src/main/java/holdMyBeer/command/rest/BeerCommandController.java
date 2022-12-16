package holdMyBeer.command.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/beer")
public class BeerCommandController {


    public String createBeer(){
        return "sussec";
    }


}