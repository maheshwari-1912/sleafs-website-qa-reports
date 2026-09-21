Feature: SLEAFS Login

Background:
Given I open the SLEAFS login page

Scenario: Login with valid credentials
When I enter a valid email and password
And I click the Sign In button
Then I should be logged in successfully

Scenario: Login with invalid password
When I enter a valid email and an invalid password
And I click the Sign In button
Then I should see a login error message

Scenario: Login with empty credentials
When I click the Sign In button
Then I should see validation messages
