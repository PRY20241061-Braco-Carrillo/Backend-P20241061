package com.p20241061.management.api.config;

import com.p20241061.management.api.mapping.SizeMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("managementMappingConfiguration")
public class MappingConfiguration {

    @Bean
    public SizeMapper sizeMapper() {
        return new SizeMapper();
    }
}
