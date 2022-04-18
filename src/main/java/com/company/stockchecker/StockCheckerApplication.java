package com.company.stockchecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
		@PropertySource("classpath:application-${spring.profiles.active}.properties")
})
public class StockCheckerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockCheckerApplication.class, args);
	}

}
