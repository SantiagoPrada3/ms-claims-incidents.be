package pe.edu.vallegrande.vg_ms_claims_incidents.application.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

   @Bean
     public OpenAPI customOpenAPI() {
          return new OpenAPI()
                    .info(new Info()
                              .title("VG Microservicio de Incidencias y Reclamos")
                              .description("API para gestión de incidencias y reclamos del sistema JASS Digital. " +
                                        "Disponible en: https://lab.vallegrande.edu.pe/jass/ms-claim-incidents/swagger-ui/index.html")
                              .version("2.0.0")
                              .contact(new Contact()
                                        .name("Valle Grande")
                                        .email("soporte@vallegrande.edu.pe")
                                        .url("https://vallegrande.edu.pe"))
                              .license(new License()
                                        .name("MIT License")
                                        .url("https://opensource.org/licenses/MIT")));
     }

} 