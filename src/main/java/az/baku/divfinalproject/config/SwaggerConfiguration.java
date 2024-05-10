package az.baku.divfinalproject.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Bearer")
                .addList(String.valueOf(new Scopes().addString("global", "access all APIs")));
        return new OpenAPI(SpecVersion.V30).info(apiInfo())
                .security(Collections.singletonList(securityRequirement))
                .components(new Components().addSecuritySchemes("Bearer",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
    private Info apiInfo() {
        Info info = new Info();
        info.title("HomeMate API");
        info.version("1.0");
        info.contact(new Contact().email("fatima.sul@div.edu.az"));
        info.contact(new Contact().name("Fatima"));
        info.contact(new Contact().url("https://github.com/fatimasultanova"));
        info.description("This API exposes endpoints to manage homemate.az").termsOfService("homemate@div.edu.az");
        return info;
    }
}