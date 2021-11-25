package netappspractical.demo.repository;

import netappspractical.demo.domain._User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<_User, Integer> {
    public _User findByEmail(String email);
}
