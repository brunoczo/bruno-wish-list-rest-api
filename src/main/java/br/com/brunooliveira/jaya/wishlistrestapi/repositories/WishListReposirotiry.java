package br.com.brunooliveira.jaya.wishlistrestapi.repositories;
import br.com.brunooliveira.jaya.wishlistrestapi.dto.WishListDTO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;


public interface WishListReposirotiry extends ReactiveMongoRepository<WishListDTO, String>{
}
