Feature: SLEAFS E-Commerce Checkout Flows

  Scenario: Flow 1 - Wishlist to cart, cart icon click, proceed to checkout, then register and login
    Given I am on the SLEAFS home page
    When I open the "Men" category
    And I open the product "Blue Sleeve"
    And I add the product to my wishlist
    And I open my wishlist
    And I move the product to cart from the wishlist
    And I open my cart
    And I proceed to checkout
    And I open the SLEAFS registration page
    And I register a new account
    And I open the SLEAFS login page
    And I enter a valid email and password
    And I click the Sign In button
    Then I should be logged in successfully
    Then the checkout page should be displayed

  Scenario: Flow 2 - Direct add to cart, cart icon click, proceed to checkout, then register and login
    Given I am on the SLEAFS home page
    When I open the "Men" category
    And I open the product "Blue Sleeve"
    And I add the product to the cart
    And I open my cart
    And I proceed to checkout
    And I open the SLEAFS registration page
    And I register a new account
    And I open the SLEAFS login page
    And I enter a valid email and password
    And I click the Sign In button
    Then I should be logged in successfully
    Then the checkout page should be displayed

  Scenario: Flow 3 - Pre-logged-in user flow starting from home page
    Given I open the SLEAFS login page
    When I enter a valid email and password
    And I click the Sign In button
    Then I should be logged in successfully
    Given I am on the SLEAFS home page
    When I open the "Men" category
    And I open the product "Blue Sleeve"
    And I add the product to the cart
    And I open my cart
    And I proceed to checkout
    Then the checkout page should be displayed