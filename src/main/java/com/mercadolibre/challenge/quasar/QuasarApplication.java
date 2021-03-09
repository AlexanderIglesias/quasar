package com.mercadolibre.challenge.quasar;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan({"com.mercadolibre.challenge.quasar.data"})
@EntityScan("com.mercadolibre.challenge.quasar.data.service.impl.db.entity")
@EnableJpaRepositories("com.mercadolibre.challenge.quasar.data.service.impl.db.repositories")
@SpringBootApplication
public class QuasarApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuasarApplication.class, args);
	}
	
	@Bean
	public Mapper mapper() {
	    return new DozerBeanMapper();
	}

}
