package netappspractical.demo.util;

import netappspractical.demo.domain.Parameter;
import netappspractical.demo.repository.EstateRepository;
import netappspractical.demo.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer implements CommandLineRunner {
    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    private EstateRepository estateRepository;

    @Override
    public void run(String... args) throws Exception {
        if(this.parameterRepository.findAll().isEmpty()) {
            // Default parameters
            this.parameterRepository.save(new Parameter("shareCount", 5.0));
            this.parameterRepository.save(new Parameter("profitPercentage", 0.20));
        }
    }
}
