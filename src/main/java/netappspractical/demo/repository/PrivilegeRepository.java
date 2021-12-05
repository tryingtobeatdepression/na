package netappspractical.demo.repository;

import netappspractical.demo.domain.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {
    public Optional<Privilege> findByName(String name);
}
