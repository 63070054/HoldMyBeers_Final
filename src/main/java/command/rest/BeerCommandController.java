package command.rest;

import org.springframework.web.bind.annotation.*;
import service.BeerService;

@RestController
@RequestMapping("/beers")
public class BeerCommandController {
//    private final BeerService beerService;
//
//    public BeerCommandController(BeerService beerService) {
//        this.beerService = beerService;
//    }

    @PostMapping
    public String createBeer(){
        return "sussec";
    }


}