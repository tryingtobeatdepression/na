package netappspractical.demo.repository;

import netappspractical.demo.domain.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Integer> {
    public Optional<Parameter> findBypKey(String string);
}
