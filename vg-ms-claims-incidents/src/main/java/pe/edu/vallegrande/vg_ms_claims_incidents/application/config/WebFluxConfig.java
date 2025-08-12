package pe.edu.vallegrande.vg_ms_claims_incidents.application.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class WebFluxConfig {

    // Comentando la configuración que causa conflicto con Swagger
    // La configuración de SpringDoc ya maneja automáticamente las rutas de Swagger
    /*
    @Bean
    public RouterFunction<ServerResponse> swaggerRouterFunction() {
        return RouterFunctions.resources("/jass/ms-claims-incidents/swagger-ui/**", new ClassPathResource("META-INF/resources/webjars/swagger-ui/"));
    }
    */
}
