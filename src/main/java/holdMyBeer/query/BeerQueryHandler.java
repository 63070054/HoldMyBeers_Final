package holdMyBeer.query;

import holdMyBeer.database.repository.BeerRepository;
import holdMyBeer.query.rest.BeerRestModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BeerQueryHandler {

    @Autowired
    private BeerRepository beerRepository;

    @QueryHandler
    List<BeerRestModel> findBeers(BeerQueryHandler query){
        List<BeerRestModel> beerRest = new ArrayList<>();
//        List<BeerEntity> storedBeer = beerRepository.findAll();
//
//        for (BeerEntity beerEntity : storedBeer){
//            BeerRestModel beerRestModel = new BeerRestModel();
//            BeanUtils.copyProperties(beerEntity, beerRestModel);
//            beerRest.add(beerRestModel);
//        }
//
        return  beerRest;
    }



}
