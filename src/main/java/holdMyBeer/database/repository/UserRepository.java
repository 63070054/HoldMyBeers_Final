package holdMyBeer.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import holdMyBeer.database.pojo.UserDB;
@Repository
public interface UserRepository extends MongoRepository<UserDB, String> {
}
