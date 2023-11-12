package br.com.brunooliveira.jaya.wishlistrestapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.annotation.Generated;
import java.util.Objects;

/**
 * ProdutoDTO
 */
@lombok.Builder @lombok.NoArgsConstructor @lombok.AllArgsConstructor
@Data
public class ProdutoDTO   {

  private String produtoId;


  private String nome;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProdutoDTO produto = (ProdutoDTO) o;
    return Objects.equals(this.produtoId, produto.produtoId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(produtoId, nome);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProdutoDTO {");
    sb.append("    produtoId: ").append(toIndentedString(produtoId));
    sb.append("    nome: ").append(toIndentedString(nome));
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

