package com.nexos.microservicio.app.inventario;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
public class MicroservicioInventarioApplication {
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
	 @Bean
	  public OpenAPI springShopOpenAPI() {
	      return new OpenAPI()
	              .info(new Info().title("Inventario API")
	              .description("Sistema de inventarios para el sector automotriz")
	              .version("v0.0.1")
	              .license(new License().name("Apache 2.0").url("http://springdoc.org")))
	              .externalDocs(new ExternalDocumentation()
	              .description("Inventarios API Documentación"));
	  }

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioInventarioApplication.class, args);
	}
}