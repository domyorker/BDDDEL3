Feature: The login process
  
  As a user of the website,
  I want to be able to login
  So that I can contribute content to the site.

  Scenario: Logging in with valid credentials
    Given the user enters "domyorker@gmail.com" and "abc"
    When the user attempts to login
    Then the user should see a greeting with their username portion of the email

     Scenario: Logging in with valid username but invalid password
      
      In the case the user inputs the wrong password, the login should be invalid
     
    Given the user enters "domyorker@gmail.com" and "def"
    When the user attempts to login
    Then the user should not be granted login
    