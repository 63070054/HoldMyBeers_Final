package holdMyBeer.query.rest;


import holdMyBeer.query.BeerQueryHandler;
import holdMyBeer.query.FindBeerQuery;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/beers")
public class BeerQueryController {

    @Autowired
    QueryGateway queryGateway;
    @GetMapping
    public List<BeerRestModel> getbeer(){
        FindBeerQuery findBeerQuery = new FindBeerQuery();
        List<BeerRestModel> beers = queryGateway.query(findBeerQuery, ResponseTypes.multipleInstancesOf(BeerRestModel.class)).join();
        return  beers;
    }



}

