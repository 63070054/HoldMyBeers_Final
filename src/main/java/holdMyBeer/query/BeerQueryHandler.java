package holdMyBeer.query;

import holdMyBeer.core.data.BeerRepository;
import holdMyBeer.query.rest.BeerRestModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BeerQueryHandler {
    private final BeerRepository beerRepository;
    public BeerQueryHandler(BeerRepository beerRepository){
        this.beerRepository = beerRepository;
    }

}
