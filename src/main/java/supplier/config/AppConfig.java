package supplier.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import supplier.entities.Supplier;
import supplier.repo.SupplierRepository;

import java.util.UUID;

@Configuration
public class AppConfig {
    @Bean
    public CommandLineRunner demo(final SupplierRepository supplierRepository) {
        return strings -> {
            Supplier supplier1 = new Supplier(UUID.randomUUID(), "Dasha", "Orlova");
            supplierRepository.save(supplier1);

        };
    }
}
