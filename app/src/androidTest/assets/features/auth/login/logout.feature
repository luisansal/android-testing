Feature: Logout

  Scenario: User should exit the app when pressing logout button
    Given The app started
    And I input username-email "luis@gmail.com" and password "123456" inputs box
    When I click on login button
    Then I see home login page screen
    When I press logout button
    Then I should exit the app