package holdMyBeer.database.repository;


import holdMyBeer.database.pojo.BeerDB;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BeerRepository extends MongoRepository<BeerDB, String> {
    @Query(value =  "{_id: '?0'}")
    public BeerDB findBeerByID(String id);

}