package br.com.brunooliveira.jaya.wishlistrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class WishListRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WishListRestApiApplication.class, args);
	}


	@Bean
	WebClient getWebClient() {
		return WebClient.create("http://localhost:8080/");
	}


}
