package br.com.brunooliveira.jaya.wishlistrestapi;

import com.rabbitmq.client.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PreDestroy;

@SpringBootApplication
public class WishListRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WishListRestApiApplication.class, args);
	}

	@Autowired
	private Mono<Connection> connectionMono;
	@Bean
	WebClient getWebClient() {
		return WebClient.create("http://localhost:8080/");
	}

	@PreDestroy
	public void close() throws Exception {
		connectionMono.block().close();
	}

}
