package com.bera.ProjectExample;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProjectExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectExampleApplication.class, args);
	}
	
	/**
     * Implementação para utilizar nos controllers(atualiza)
     * 
     * @param args
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }

}
