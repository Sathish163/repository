Feature: Testing ReactJS Site

  Scenario: Verify Main Concepts and Advanced Guides
    Given I choose to use the "Chrome" browser
    Given I open the ReactJS site
    When I navigate to the "Docs" tab
    And I expand the "Main Concepts" section
    Then I should see "Main Concepts" values
    And I expand the "Advanced Guides" section
    Then I should see "Advanced Guides" values
    And I quit the browser
