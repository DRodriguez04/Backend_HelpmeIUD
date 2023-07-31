package co.edu.iudigital.iudhelpme.config;


import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("co.edu.iudigital.iudhelpme.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "API Helpme IUDigital",
                "API Rest app Helpme IUDigital",
                "1.0",
                "https://www.iudigital.edu.co/",
                new Contact("Daniela Rodriguez", "https://www.iudigital.edu.co/", "email@iudigital.edu.co"),
                "LICENSE",
                "LICENSE URL",
                Collections.emptyList()
        );
    }

    //http://localhost:8082/api-helpmeiud/v1/swagger-ui.html

    //http://localhost:8082/api-helpmeiud/v1/v2/api-docs
}
