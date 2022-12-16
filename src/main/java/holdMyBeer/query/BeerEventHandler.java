package holdMyBeer.query;

import holdMyBeer.core.BeerEntity;
import holdMyBeer.core.data.BeerRepository;
import holdMyBeer.core.event.BeerCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;

public class BeerEventHandler {
    private final BeerRepository beerRepository;

    public BeerEventHandler(BeerRepository beerRepository){
        this.beerRepository = beerRepository;
    }

    @EventHandler
    public void on(BeerCreatedEvent event){
        BeerEntity beerEntity = new BeerEntity();
        BeanUtils.copyProperties(event, beerEntity);
        beerRepository.save(beerEntity);

    }
}
