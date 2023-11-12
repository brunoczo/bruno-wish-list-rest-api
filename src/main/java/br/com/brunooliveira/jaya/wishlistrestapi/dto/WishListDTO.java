package br.com.brunooliveira.jaya.wishlistrestapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * WishListDTO
 */
@lombok.Builder @lombok.NoArgsConstructor @lombok.AllArgsConstructor
@Data
@Document
public class WishListDTO   {


  @Id
  private String usuarioId;


  @Valid
  private List<ProdutoDTO> listProdutos = new ArrayList<>();


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WishListDTO wishList = (WishListDTO) o;
    return Objects.equals(this.usuarioId, wishList.usuarioId) ;
  }

  @Override
  public int hashCode() {
    return Objects.hash(usuarioId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WishListDTO {");
    sb.append("    usuarioId: ").append(toIndentedString(usuarioId));
    sb.append("    listProdutos: ").append(toIndentedString(listProdutos));
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

