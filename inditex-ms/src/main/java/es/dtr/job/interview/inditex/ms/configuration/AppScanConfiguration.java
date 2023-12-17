package es.dtr.job.interview.inditex.ms.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "es.dtr")
@ComponentScan(basePackages = "es.dtr")
public class AppScanConfiguration {
}
