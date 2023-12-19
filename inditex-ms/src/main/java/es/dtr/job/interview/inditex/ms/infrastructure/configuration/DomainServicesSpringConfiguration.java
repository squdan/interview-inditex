package es.dtr.job.interview.inditex.ms.infrastructure.configuration;

import es.dtr.job.interview.inditex.ms.domain.service.brand.BrandService;
import es.dtr.job.interview.inditex.ms.domain.service.price.PriceService;
import es.dtr.job.interview.inditex.ms.domain.service.product.ProductService;
import es.dtr.job.interview.inditex.ms.domain.service.user.UserService;
import es.dtr.job.interview.inditex.ms.infrastructure.database.brand.BrandAdapterRepository;
import es.dtr.job.interview.inditex.ms.infrastructure.database.price.PriceAdapterRepository;
import es.dtr.job.interview.inditex.ms.infrastructure.database.product.ProductAdapterRepository;
import es.dtr.job.interview.inditex.ms.infrastructure.database.user.UserAdapterRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DomainServicesSpringConfiguration {

    @Bean
    BrandService brandService(final BrandAdapterRepository repository) {
        return new BrandService(repository);
    }

    @Bean
    PriceService priceService(final PriceAdapterRepository repository) {
        return new PriceService(repository);
    }

    @Bean
    ProductService productService(final ProductAdapterRepository repository) {
        return new ProductService(repository);
    }

    @Bean
    UserService userService(final UserAdapterRepository repository, final PasswordEncoder passwordEncoder) {
        return new UserService(repository, passwordEncoder);
    }
}
