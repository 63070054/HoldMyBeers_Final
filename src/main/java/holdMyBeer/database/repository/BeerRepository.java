package holdMyBeer.database.repository;


import holdMyBeer.database.pojo.Beer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BeerRepository extends MongoRepository<Beer, String> {
    @Query(value =  "{_id: '?0'}")
    public Beer findBeerByID(String id);

}