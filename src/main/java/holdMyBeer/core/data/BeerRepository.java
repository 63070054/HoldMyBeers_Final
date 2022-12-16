package holdMyBeer.core.data;


import holdMyBeer.core.BeerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BeerRepository extends MongoRepository<BeerEntity, String> {
    BeerEntity findByBeerId(String BeerId);
}