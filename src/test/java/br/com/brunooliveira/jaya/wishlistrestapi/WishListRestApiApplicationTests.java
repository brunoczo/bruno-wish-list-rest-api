package br.com.brunooliveira.jaya.wishlistrestapi;

import br.com.brunooliveira.jaya.wishlistrestapi.dto.ProdutoDTO;
import br.com.brunooliveira.jaya.wishlistrestapi.dto.WishListDTO;


import cucumber.api.Result;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.reactive.server.WebTestClient;


import java.util.Date;


    public class WishListRestApiApplicationTests extends WishListIntegrationTest {




    private WebTestClient.ResponseSpec exchange;

    WishListDTO wishListDTO = null;
    ProdutoDTO produtoDTO = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(WishListRestApiApplicationTests.class);

        // CENARIO 1
        @Given("O cliente precisa cadastrar um novo produto")
        public void cliente_precisa_cadastrar_novo_produto() throws Exception {
            cleanProdutos();

        }

        public void cleanProdutos() throws Exception {
            //CLEAN TESTs
            wishListDTO = getWishList("12345").block();
            if(wishListDTO!=null && wishListDTO.getListProdutos() != null){
                for (ProdutoDTO produto  :  wishListDTO.getListProdutos() ) {
                    try {
                        LOGGER.info("Deletando: " + produto);
                        deleteProduct("12345",produto.getProdutoId() ).block();
                    }catch (Exception e){

                    }
                }
            }
        }
        @Then("o cliente com id {} cadastra o produto id {} e nome {}")
        public void cadastro_produto(String idUsuario ,String produtoId,String produtoNome) throws Exception {
            produtoDTO = cadastroProduto(""+idUsuario,""+produtoId,produtoNome).block();
        }
        @Then("o cliente recebe produto")
        public void verifica_produto() throws Exception {

            Assert.assertNotNull(produtoDTO);
            produtoDTO = null;

        }

        // CENARIO 2
        @Given("O cliente precisa visualiza o novo produto")
        public void cliente_precisa_visualiza_novo_produto() throws Exception {

        }
        @Then("o cliente busca o produto com id {} o produto com id {}")
        public void busca_produto(String idUsuario,String idProduto) throws Exception {
            LOGGER.info("-" + idUsuario + " - " +idProduto );
            produtoDTO = getProduct(""+idUsuario,""+idProduto).block();
        }


        // CENARIO 3
        @Given("O cliente precisa visualiza todos os seus produtos")
        public void cliente_precisa_visualiza_todos_produto() throws Exception {

        }

        @Then("o cliente {} busca todos os seus produtos")
        public void busca_wishlist(String idUsuario) throws Exception {
            wishListDTO = getWishList(idUsuario).block();
            LOGGER.info( "-> " + wishListDTO);
        }
        @Then("o cliente tem {} produtos")
        public void verifica_wishlist( int produtos) throws Exception {
            LOGGER.info( "-> " + wishListDTO);
            Assert.assertEquals(produtos, wishListDTO.getListProdutos().size());
        }
        // CENARIO 4
        @Given("O cliente precisa delatar os novos produto")
        public void cliente_precisa_deletar_novo_produto() throws Exception {
        }
        @Then("o cliente {} deleta o produto {}")
        public void deleta_produto(String idUsuario,String idProduto) throws Exception {
            deleteProduct(idUsuario,idProduto).block();
        }



        // CENARIO 5
        @Given("O cliente tem um limite de cadastro")
        public void cliente_tem_limite_produto() throws Exception {
        }
        @Then("o cliente {} cadastra {} produtos")
        public void cadastra_x_produtos(String idUsuario, int qtd) throws Exception {
            for (int i = 0 ; i < qtd ; i++) {
                produtoDTO = cadastroProduto(idUsuario,""+i,"produto" +i).block();
            }

        }
        @Then("tenta cadastrar mais 1 a aplicação retorna um erro")
        public void cadastra_1_produto_erros() throws Exception {
            try {
                produtoDTO = cadastroProduto("12345","21","produto21" ).block();
                wishListDTO = getWishList("12345").block();
                LOGGER.info("Produtos: " +wishListDTO.getListProdutos().size()  );
                Assert.fail("A aplicação deixou cadastrar mais de X produtos");
            }catch (Exception e){

            }

        }
        @Then("a lista de produtos é removida")
        public void gera_wishlist_vazia() throws Exception {
            cleanProdutos();
        }




}
