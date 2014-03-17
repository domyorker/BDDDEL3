Feature: The registration process
  
  As an admistrator of the website
  I want to capture valid email addresses during registration
  So that a user may recover a forgotten password and to deter bogus registrations

  Scenario: Registering with a valid email and password
     
     This is a base case where a user provides an email in the right format,
     in addition to a password at least six characters long.

    Given the user enters "test@test.com" and "abc123"
    When registering for the first time
    Then the user should be registered successfully

  Scenario: Attempting to register with an already registered email
    
    This case ensures the system does not allow the same email to be used more than once.

    Given the user enters "domyorker@gmail.com" and "abc123"
    When registering for the first time
    Then the user should not be registered

  Scenario: Attempting to register with an password shorter than 6 characters
    
    This case ensures the system does not allow the input of a password too short
    to meet the recommended password length of 6 characters.

    Given the user enters "test2@test.com" and "a"
    When registering for the first time
    Then the user should not be registered

  Scenario Outline: Registering with an invalid-formatted email
    
    This check is crucial in trying to deter bogus registrations

    Given the user enters "<email>" and "<password>"
    When registering for the first time
    Then the user should not be registered

    Examples: 
      | email       | password |
      | d@          | abc123   |
      | bo.@gus.com | abc123   |
      | a@b         | abc123   |
      | d@.com      | abc123   |
      | abc\\@d.com | abc123   |
      | d.dom@com   | abc123   |
