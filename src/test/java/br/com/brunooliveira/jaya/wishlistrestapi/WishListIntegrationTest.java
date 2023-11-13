package br.com.brunooliveira.jaya.wishlistrestapi;

import br.com.brunooliveira.jaya.wishlistrestapi.dto.ProdutoDTO;
import br.com.brunooliveira.jaya.wishlistrestapi.dto.WishListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatusCode;
import org.springframework.restdocs.ManualRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ContextConfiguration(classes = WishListRestApiApplication.class)
public class WishListIntegrationTest {
    @Autowired
    private ApplicationContext applicationContext;
    WebTestClient webTestClient;
    @Autowired
    private WebClient webClient;

    ManualRestDocumentation manualRestDocumentation;

    public void setUp(Class testClass, String testMethod, String outputDirectory) {
        manualRestDocumentation = new ManualRestDocumentation(outputDirectory);
        manualRestDocumentation.beforeTest(testClass, testMethod);

        webTestClient = WebTestClient.bindToApplicationContext(applicationContext)

                // Applying Spring Restdocs and Spring Cloud Contract
                .configureClient().baseUrl("http://localhos:8080")// Spring Restdocs' document() will fail if baseUrl isn't set
                .build();
    }

    Mono<WishListDTO> getWishList(String userId ) throws Exception {

        return webClient.get()
                .uri("/wishlist/"+userId)
                .retrieve()

                .bodyToMono(WishListDTO.class);
    }

    Mono<ProdutoDTO> getProduct(String userId , String produtoId) throws Exception {

        return webClient.get()
                .uri("/wishlist/"+userId+"/"+produtoId)
                .retrieve()

                .bodyToMono(ProdutoDTO.class);
    }

    Mono<Void> deleteProduct(String userId , String produtoId) throws Exception {

        return webClient.delete()
                .uri("/wishlist/"+userId+"/"+produtoId)
                .retrieve()

                .bodyToMono(Void.class);
    }


    Mono<ProdutoDTO> cadastroProduto(String userId , String produtoId, String produtoNome ) throws Exception {

        ProdutoDTO requestBody = new ProdutoDTO(produtoId,produtoNome);

        return webClient.post()
                .uri("/wishlist/"+userId)
                .bodyValue(requestBody)
                .retrieve()

                .bodyToMono(ProdutoDTO.class);
    }


}
