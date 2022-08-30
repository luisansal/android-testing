Feature: Login
  # Tests for the list of groceries and the details of a particular grocery

  # Login contains username|email and password
  Scenario: User should login in the app
    Given The app started
    And I fill username-email "luis@gmail.com" and password "123456" inputs box
    When I click on login button
    Then I see home login page screen

  @wip
  Scenario: User wrong password attemps exception
    Given The app started
    When I press logout button
    Then I should exit the app