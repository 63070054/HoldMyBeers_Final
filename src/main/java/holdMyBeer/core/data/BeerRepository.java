package holdMyBeer.core.data;


import holdMyBeer.core.BeerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerRepository extends JpaRepository<BeerEntity, String> {
    BeerEntity findByBeerId(String beerId);
}