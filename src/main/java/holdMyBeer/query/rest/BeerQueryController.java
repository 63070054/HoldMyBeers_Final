package holdMyBeer.query.rest;


import holdMyBeer.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/beers")
public class BeerQueryController {

    private BeerService beerService;

    @GetMapping("/{BeerId}")
    public String createBeer(){
        return "sussec";
    }

}

