package netappspractical.demo.repository;

import netappspractical.demo.domain._User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<_User, Integer> {
    public Optional<_User> findByEmail(String email);
}
