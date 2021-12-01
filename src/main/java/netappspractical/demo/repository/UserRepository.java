package netappspractical.demo.repository;

import netappspractical.demo.domain._User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<_User, Integer> {
    public _User findByEmail(String email);
}
