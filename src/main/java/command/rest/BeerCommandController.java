package command.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.BeerService;

@RestController
@RequestMapping("/Beer")
public class BeerCommandController {
    private final BeerService beerService;

    public BeerCommandController(BeerService beerService) {
        this.beerService = beerService;
    }
    @PostMapping
    public String createBeer(@RequestBody CreateBeerRestModel model){
        return "sussec" + model.getBeerName();
    }


}
