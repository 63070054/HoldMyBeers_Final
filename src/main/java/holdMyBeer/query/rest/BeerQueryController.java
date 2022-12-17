package holdMyBeer.query.rest;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/beers")
public class BeerQueryController {
    @Autowired
    private BeerService beerService;

    // Read All Beer
    @GetMapping()
    public BeerRestModel getAllBeer() {
        return beerService.getBeers();
    }
    // Read Beer By id
    @GetMapping("/{id}")
    public BeerRestModel getBeer(@PathVariable Long id) {
        return beerService.getBeerByBeerId(id);
    }

    // Read Beer By name
    @GetMapping("/{name}")
    public List<Beer> getBeer(@PathVariable String  name) {
        return beerService.getBeerByName(name);
    }



}

