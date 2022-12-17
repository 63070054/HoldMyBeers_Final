package holdMyBeer.query.rest;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/beers")
public class BeerQueryController {
    @Autowired
    private BeerService beerService;

    // Read Beer By id
    @GetMapping("/{id}")
    public BeerRestModel getBeer(@PathVariable Long id) {
        return beerService.getBeerById(id);
    }

    // Read Beer By name
    @GetMapping("/{name}")
    public List<Beer> getBeer(@PathVariable String  name) {
        return beerService.getBeerByName(name);
    }



}

