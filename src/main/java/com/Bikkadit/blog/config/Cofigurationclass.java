package com.Bikkadit.blog.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class Cofigurationclass {
	@Bean
public ModelMapper Mapper() {
		
	return new ModelMapper();
	
}

}
