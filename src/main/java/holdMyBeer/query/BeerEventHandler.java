package holdMyBeer.query;

import holdMyBeer.database.repository.BeerRepository;
import holdMyBeer.database.event.CreateBeerEvent;
import org.axonframework.eventhandling.EventHandler;

public class BeerEventHandler {
    private final BeerRepository beerRepository;

    public BeerEventHandler(BeerRepository beerRepository){
        this.beerRepository = beerRepository;
    }

    @EventHandler
    public void on(CreateBeerEvent event){
//        Beer beer = new Beer();
//        BeanUtils.copyProperties(event, beer);
//        beerRepository.save(beer);

    }
}
