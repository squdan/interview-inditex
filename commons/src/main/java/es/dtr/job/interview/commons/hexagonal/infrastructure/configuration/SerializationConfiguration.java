package es.dtr.job.interview.commons.hexagonal.infrastructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SerializationConfiguration {

    ObjectMapper objectMapper() {
        final ObjectMapper result = new ObjectMapper();
        result.registerModule(new JavaTimeModule());
        return result;
    }
}
