package com.cos.webflux01.config;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import com.cos.webflux01.domain.Customer;
import com.cos.webflux01.domain.CustomerRepository;

import io.r2dbc.spi.ConnectionFactory;

@Configuration
public class DBInit {

	// 데이터베이스를 초기화 할 수 있음
	// r2dbc를 사용할 때 데이터를 초기화함.
	@Bean
	public ConnectionFactoryInitializer dbInit(ConnectionFactory connectionFactory) {
		ConnectionFactoryInitializer init = new ConnectionFactoryInitializer();
		init.setConnectionFactory(connectionFactory);
		init.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
		return init;
	}

	@Bean
	public CommandLineRunner dataInit(CustomerRepository customerRepository) {
		return (args) -> {
			// 데이터 초기화 하기
			customerRepository.saveAll(Arrays.asList(
					new Customer("Jack", "Bauer"), 
					new Customer("Choi", "Bauer"),
					new Customer("Hong", "Bauer"), 
					new Customer("Han", "Bauer"), 
					new Customer("Joo", "Bauer")
					)
					// blockLast 지금 하고 있는 비동기 종료 시킴
					// 초기화 할 때는 꼭 써주자 !
				).blockLast(); // flux 끝
		};
	}
}