package es.dtr.job.interview.inditex.ms.configuration;

import es.dtr.job.interview.commons.api.ApiConfiguration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@OpenAPIDefinition(info = @Info(title = OpenApiConfiguration.API_NAME, version = OpenApiConfiguration.API_VERSION, description = OpenApiConfiguration.API_DESCRIPTION))
@SecurityScheme(name = ApiConfiguration.API_SECURITY_NAME, type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class OpenApiConfiguration implements WebMvcConfigurer {

    public static final String API_NAME = "MS Application API";
    public static final String API_DESCRIPTION = "MS generado para la entrevista.";
    public static final String API_VERSION = "1.0";
    public static final String API_PATH = "/api/" + API_VERSION;

    /**
     * Sobrescribimos la vista del acceso al app sin nada para que redirija al
     * swagger-ui.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/swagger-ui.html");
    }
}
