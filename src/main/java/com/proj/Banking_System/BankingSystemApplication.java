package com.proj.Banking_System;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info=@Info(
				title ="Vikas Patil Bank",
				description = "Backent Rest API's Banking",
				contact = @Contact(
						name="Vikas Patil",
						email = "patilvikas580@gmail.com",
						url="https://github.com/patilvikas580/Banking-System-Java-SpringBoot-"
				),
				license = @License(
						name="Vikas_Patil",
						url="https://github.com/patilvikas580/Banking-System-Java-SpringBoot-"
				)

		),
		externalDocs = @ExternalDocumentation(
				description = "Banking App",
				url="https://github.com/patilvikas580/Banking-System-Java-SpringBoot-"
		)
)
public class BankingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingSystemApplication.class, args);
	}

}
