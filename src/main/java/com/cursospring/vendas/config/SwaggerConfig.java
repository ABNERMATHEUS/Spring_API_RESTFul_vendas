package com.cursospring.vendas.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                        .useDefaultResponseMessages(false)
                        .select().apis(RequestHandlerSelectors.basePackage("com.cursospring.vendas.controler"))
                        .paths(PathSelectors.any())
                        .build()
                        .securityContexts(Arrays.asList(securityContext())) //Objeto para configurar segurança JWT
                        .securitySchemes(Arrays.asList(apiKey()))           //Objeto para configurar segurança JWT
                        .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("API RESTful Vendas ")
                                   .description("Api do projeto de Venda")
                                   .version("1.0")
                                   .contact(contact())
                                   .build();
    }

    private Contact contact(){
        return new Contact("Abner Matheus","https://github.com/ABNERMATHEUS", "Abnerm80@gmail.com");
    }

    private ApiKey apiKey(){
        return new ApiKey( "JWT","Authorization","header");
    }

    private List<SecurityReference> defaultAuth(){  //Criando um scopo global dando referencia a a apiKey do metodo de cima
        AuthorizationScope authorizationScope = new AuthorizationScope("global","accessEverything");
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = authorizationScope;
        SecurityReference reference = new SecurityReference("JWT",scopes);
        List<SecurityReference> auths = new ArrayList<>();
        auths.add(reference);
        return auths;
    }

    private SecurityContext securityContext(){
        return  SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
    }


}
