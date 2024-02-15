package com.project.ems.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
      info = @Info(
            title = "Employee Management System REST API",
            description = "This is a sample REST API documentation of an employee management system server.",
            version = "1.0.0",
            contact = @Contact(name = "George Bacalu", email = "georgebacalu83@gmail.com")),
      tags = {
            @Tag(name = "authority", description = "Authority REST Controller"),
            @Tag(name = "employee", description = "Employee REST Controller"),
            @Tag(name = "experience", description = "Experience REST Controller"),
            @Tag(name = "feedback", description = "Feedback REST Controller"),
            @Tag(name = "role", description = "Role REST Controller"),
            @Tag(name = "study", description = "Study REST Controller"),
            @Tag(name = "trainer", description = "Trainer REST Controller"),
            @Tag(name = "user", description = "User REST Controller")})
public class OpenApiConfig {
}
