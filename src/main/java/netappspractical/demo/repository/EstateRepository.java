package netappspractical.demo.repository;

import netappspractical.demo.domain.Estate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface EstateRepository extends JpaRepository<Estate, Integer> {
    /**
     * Lock with a Pessimistic Lock so that no one can edit the data being requested.
     *
     * @return
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Override
    public List<Estate> findAll();
}
