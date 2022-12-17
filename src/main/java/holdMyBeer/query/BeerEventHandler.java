package holdMyBeer.query;

import holdMyBeer.database.Beer;
import holdMyBeer.database.repository.BeerRepository;
import holdMyBeer.database.event.BeerCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;

public class BeerEventHandler {
    private final BeerRepository beerRepository;

    public BeerEventHandler(BeerRepository beerRepository){
        this.beerRepository = beerRepository;
    }

    @EventHandler
    public void on(BeerCreatedEvent event){
        Beer beer = new Beer();
        BeanUtils.copyProperties(event, beer);
        beerRepository.save(beer);

    }
}
