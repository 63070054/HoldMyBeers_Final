package holdMyBeer.command.rest;

import holdMyBeer.query.rest.BeerRestModel;
import holdMyBeer.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/beers")
public class BeerCommandController {

    // Create Beer
    @PostMapping
    public void createBeer(@RequestBody BeerRestModel beerRestModel){
//        BeerRestModel beerRestModel = new BeerRestModel();
//        beerRestModel.setBeerName(request.getBeerName());
//        return beerRestModel;
//        return beerService.createBeer(beerRestModel);
    }
    // Update Beer
    @PutMapping("/{id}")
    public void updateBeer(@PathVariable Long id, @RequestBody BeerRestModel beerRestModel) {
//        return beerService.updateBeer(id, beerRestModel);
    }

    // Delete Beer
    @DeleteMapping("/{id}")
    public void deleteBeer(@PathVariable Long id) {
//        beerService.deleteQuery(id);
    }


}