package holdMyBeer.query.rest;

import holdMyBeer.core.data.BeerRepository;
import holdMyBeer.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/beers")
public class BeerQueryController {

    @Autowired
    private BeerService beerService;

    @GetMapping("/{BeerId}")
    public BeerRepository getData(@PathVariable("BeerId") String BeerId) {
        String data = beerService.getData(BeerId);

    }
}

