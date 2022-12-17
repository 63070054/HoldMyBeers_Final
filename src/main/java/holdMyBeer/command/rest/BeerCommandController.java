package holdMyBeer.command.rest;

import holdMyBeer.query.rest.BeerRestModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/beers")
public class BeerCommandController {
    @Autowired
    private BeerService beerService;

    // Create Beer
    @PostMapping
    public BeerRestModel createBeer(@RequestBody BeerRestModel beerRestModel){
//        BeerRestModel beerRestModel = new BeerRestModel();
//        beerRestModel.setBeerName(request.getBeerName());
//        return beerRestModel;
        return beerService.createBeer(beerRestModel);
    }
    // Update Beer
    @PutMapping("/{id}")
    public BeerRestModel updateBeer(@PathVariable Long id, @RequestBody BeerRestModel beerRestModel) {
        return beerService.updateBeer(id, beerRestModel);
    }

    // Delete Beer
    @DeleteMapping("/{id}")
    public void deleteBeer(@PathVariable Long id) {
        beerService.deleteQuery(id);
    }


}