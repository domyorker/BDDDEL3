Feature: The authoring process
  As a user of the website,
  I want to be able to access the authoring page after logging in,
  So that I can contribute content to the site.

  Scenario: Accessing the authoring page after a valid login
    Given the user enters "domyorker@gmail.com" and "abc"
    When the user attempts to login
    Then the user should be granted access to the authoring page

  Scenario: Accessing the authoring page after logging out
    Given the user enters "domyorker@gmail.com" and "abc"
    When the user attempts to login
    And the user logs out
    Then the user should not be able to access the authoring page
