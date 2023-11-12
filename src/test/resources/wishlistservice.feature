Feature: Verify the wishList service

  Scenario: client makes call to GET wishlist
    When the client calls /wishlist
    Then the client receives status code of 200
    And the client receives wishlist with name teste