package br.com.brunooliveira.jaya.wishlistrestapi;

import br.com.brunooliveira.jaya.wishlistrestapi.dto.WishListDTO;


import org.junit.Before;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.reactive.server.WebTestClient;


import java.util.Date;


    public class WishListRestApiApplicationTests extends WishListIntegrationTest {



    @Before
    public void setup() {
        LOGGER.info("Before any test execution");
        super.setUp(CucumberTest.class, "setUp", "target/generated-snippets/contacts");

    }
        private WebTestClient.ResponseSpec exchange;

    WishListDTO wishListDTO = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(WishListRestApiApplicationTests.class);




    @When("the client calls \\/wishlist")
    public void the_client_calls_getProduct() throws Exception {
        LOGGER.info("Running: I request all persons at " + new Date());
        exchange = webTestClient.mutateWith(mockUser()).get().uri(path).exchange();
       //wishListDTO = getWishList().block();
    }

   /* @Then("the client receives status code of {int}")
    public void the_client_receives_status_code_of(Integer int1) throws Exception {
        //action.andExpect(status().isOk());
    }

    @Then("the client receives product with name")
    public void the_client_receives_product_with_name() throws Exception {
        //action.andExpect(jsonPath("name", Matchers.is("Test")));
    }*/
}
