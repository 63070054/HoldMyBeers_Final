package holdMyBeer.database.repository;

import holdMyBeer.database.pojo.Beer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import holdMyBeer.database.pojo.User;
@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
