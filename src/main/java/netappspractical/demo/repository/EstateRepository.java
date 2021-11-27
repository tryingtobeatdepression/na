package netappspractical.demo.repository;

import netappspractical.demo.domain.Estates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstateRepository extends JpaRepository<Estates, Integer> {

}
