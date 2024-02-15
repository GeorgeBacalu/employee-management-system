package com.project.ems.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class ProjectConfig {

    @Value("${app.timezone}")
    private String timeZone;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Clock clock() {
        return Clock.system(ZoneId.of(timeZone));
    }
}
