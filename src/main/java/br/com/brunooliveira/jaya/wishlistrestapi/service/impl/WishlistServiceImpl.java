package br.com.brunooliveira.jaya.wishlistrestapi.service.impl;
import br.com.brunooliveira.jaya.wishlistrestapi.dto.ProdutoDTO;
import br.com.brunooliveira.jaya.wishlistrestapi.dto.WishListDTO;
import br.com.brunooliveira.jaya.wishlistrestapi.repositories.WishListReposirotiry;

import br.com.brunooliveira.jaya.wishlistrestapi.service.WishlistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Component
public class WishlistServiceImpl implements WishlistService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WishlistServiceImpl.class);
    @Value("${wishlist.cache.ttl_days}")
    private final Integer CACHE_TTL_DAYS = 3;
    private final Duration TTL_DAYS = Duration.ofDays(CACHE_TTL_DAYS);
    @Value("${wishlist.maxprodutos}")
    private final Integer MAX_PRODUTOS = 20;
    @Autowired
    private ReactiveRedisTemplate<String, WishListDTO> redisTemplate;
    private ReactiveValueOperations<String, WishListDTO> reactiveValueOps;
    @Autowired
    WishListReposirotiry wishListReposirotiryy;
    @PostConstruct
    public void setup() {
        reactiveValueOps = redisTemplate.opsForValue();
    }

     public Mono<ResponseEntity<Void>> deleteProduto(String requestId,String userId, String produtoId,  ServerWebExchange exchange) {
        return buscaWishListByID( requestId, userId)
                .flatMap(wl -> deletaProductArrayEsalva(requestId,wl,produtoId ))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
    public Mono<ResponseEntity<Void>> deletaProductArrayEsalva(String requestId , WishListDTO wl, String produtoId){

            ProdutoDTO produtoDTO = wl.getListProdutos()
                    .stream()
                    .filter((a )-> a.getProdutoId().equals(produtoId) )
                    .findFirst().orElse(null);
            if(produtoDTO == null){
                return Mono.just(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
            }
            wl.getListProdutos().remove(produtoDTO);
            return  saveWishList(requestId,wl).map(a -> new ResponseEntity<Void>(HttpStatus.OK)) ;

        }

    public Mono<ResponseEntity<Void>> deleteCache(String requestId,String userId, ServerWebExchange exchange) {
        return  reactiveValueOps.get(userId).flatMap(wl->{
                    return reactiveValueOps.delete(userId).map(a->{
                        return new ResponseEntity<Void>(HttpStatus.OK);
                    });
                })
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }



    public Mono<ResponseEntity<WishListDTO>> getWishListByUsuarioId(String requestId,String userId,  ServerWebExchange exchange) {

        return  buscaWishListByID( requestId, userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }
    public Mono<WishListDTO> buscaWishListByID(String requestId,String userId){
        return  reactiveValueOps.get(userId).map(wl -> wl)
                .switchIfEmpty(Mono.defer(() -> {
                    LOGGER.info("[{}] buscaWishListByID cache vazio, busca pelo db ",requestId);
                            return wishListReposirotiryy.findById(userId)
                                    .flatMap(wlMongo -> saveWishList(requestId,wlMongo) )
                                    .map(wlMongo ->{
                                        return wlMongo;
                                    });
                        })
                );
    }

    public Mono<ResponseEntity<ProdutoDTO>> getProdutoByUsuarioIdAndProdutoId(String requestId, String userId, String produtoId, ServerWebExchange exchange) {

       return buscaWishListByID( requestId, userId)
               .flatMap(wl ->  buscaProduto(requestId, wl,produtoId) )
               .map(produtoDTO ->  new ResponseEntity<>(produtoDTO,HttpStatus.OK))
               .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    public Mono<WishListDTO> saveWishList(String requestId ,  WishListDTO wl){
        return reactiveValueOps.set(wl.getUsuarioId(),wl, TTL_DAYS )
            .flatMap((status) ->{
                LOGGER.info("[{}] saveWishList salvo {} ",requestId,status);
                return wishListReposirotiryy.save(wl)
                    .map(wlMongo -> wlMongo);

        });

    }
 public  Mono<ProdutoDTO> buscaProduto(String requestId , WishListDTO wl, String produtoId){
     LOGGER.info("[{}] buscaProduto buscando produto por Id. wl: {} produdtoId: {}",requestId, wl, produtoId);
     ProdutoDTO produtoDTO = wl.getListProdutos()
             .stream()
             .filter((a )-> a.getProdutoId().equals(produtoId) )
             .findFirst().orElse(null);
     if(produtoDTO == null){
         LOGGER.info("[{}] buscaProduto Produto nao encontrado",requestId);

         return Mono.empty();
     }
     LOGGER.info("[{}] buscaProduto produto encontrado: {}",requestId,  produtoDTO);

     return Mono.just(produtoDTO);
 }



    public Mono<ResponseEntity<ProdutoDTO>> postProduto(String requestId,String userId, ProdutoDTO produtoDTO,   ServerWebExchange exchange) {

        return buscaWishListByID( requestId, userId).flatMap(wl -> {
            LOGGER.info("[{}] postProduto product: {}  ",requestId,produtoDTO);
            if(wl.getListProdutos().contains(produtoDTO)){
                return  Mono.just(new ResponseEntity<>(produtoDTO,HttpStatus.OK));
            }
            if(wl.getListProdutos().size() >= MAX_PRODUTOS ){

                return  Mono.just(new ResponseEntity<>(produtoDTO,HttpStatus.UNPROCESSABLE_ENTITY));
            }
            wl.getListProdutos().add(produtoDTO);
            return  saveWishList(requestId,wl).map(a -> new ResponseEntity<>(produtoDTO,HttpStatus.OK)) ;
        }).switchIfEmpty( Mono.defer(() -> {
            LOGGER.info("[{}] postProduto criando na wishlist",requestId);
            WishListDTO wl = new WishListDTO();
            wl.getListProdutos().add(produtoDTO);
            wl.setUsuarioId(userId);

           return  saveWishList(requestId,wl).map(a -> new ResponseEntity<>(produtoDTO,HttpStatus.CREATED)) ;


        }));

    }



}
