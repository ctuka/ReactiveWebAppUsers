package com.tevfikkoseli.wf.users.configuration;

import com.tevfikkoseli.wf.users.dto.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public Mapper getMapper() {
        return new Mapper();
    }
}
