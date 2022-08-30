@wip
Feature: Logout

  Scenario: User should exit the app when pressing logout button
    Given The app started
    And I see home login page screen
    When I press logout button
    Then I should exit the app