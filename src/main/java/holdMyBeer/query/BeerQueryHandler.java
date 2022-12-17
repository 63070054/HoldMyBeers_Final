package holdMyBeer.query;

import holdMyBeer.core.BeerEntity;
import holdMyBeer.core.data.BeerRepository;
import holdMyBeer.query.rest.BeerRestModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BeerQueryHandler {
    private final BeerRepository beerRepository;
    public BeerQueryHandler(BeerRepository beerRepository){
        this.beerRepository = beerRepository;
    }

    @QueryHandler
    List<BeerRestModel> findBeers(BeerQueryHandler query){
        List<BeerRestModel> beerRest = new ArrayList<>();
        List<BeerEntity> storedBeer = beerRepository.findAll();

        for (BeerEntity beerEntity : storedBeer){
            BeerRestModel beerRestModel = new BeerRestModel();
            BeanUtils.copyProperties(beerEntity, beerRestModel);
            beerRest.add(beerRestModel);
        }

        return  beerRest;
    }



}
