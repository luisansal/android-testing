@login
Feature: Iniciar sesion

  Scenario: User should login in the app
    Given The app started
    And I input username-email "luis@gmail.com" and password "123456" inputs box
    When I click on login button
    Then I see home login page screen

  Scenario: User wrong password attemp exception
    Given The app started
    And I input username-email "luis@gmail.comm" and password "123456" inputs box
    When I click on login button
    Then I see exception popup



