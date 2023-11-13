Feature: WishList (Lista de desejos)
  Serviço que controla a lista de desejos de um cliente,
  O cliente pode inserir um produto, limitado a 20 produtos.
  Também visualizar todos os produto ou apenas um produto pelo seu Id.
  Além destas opções o cliente pode deletar um produto pelo seu Id.

  Scenario: O cliente precisa cadastrar um novo produto
    When  o cliente com id 12345 cadastra o produto id 1 e nome Produto1
    Then o cliente recebe produto


  Scenario: O cliente precisa visualiza o novo produto
    When o cliente busca o produto com id 12345 o produto com id 1
    Then o cliente recebe produto


  Scenario: O cliente precisa visualiza todos os seus produtos
    When o cliente com id 12345 cadastra o produto id 2 e nome Produto2
    Then o cliente 12345 busca todos os seus produtos
     And o cliente tem 2 produtos

  Scenario: O cliente precisa delatar os novos produto
    When o cliente 12345 deleta o produto 1
    When o cliente 12345 deleta o produto 2
    Then o cliente 12345 busca todos os seus produtos
    And o cliente tem 0 produtos

  Scenario: O cliente tem um limite de cadastro
    When o cliente 12345 cadastra 20 produtos
    Then tenta cadastrar mais 1 a aplicação retorna um erro
    And a lista de produtos é removida

