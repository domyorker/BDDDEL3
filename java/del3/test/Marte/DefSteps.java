package del3.test.Marte;

import java.util.concurrent.TimeUnit;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import static org.junit.Assert.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DefSteps {
	// declare a few class-level variables...
	private String accountEmail;
	private String userName;
	private String accountPassword;
	WebDriver driver = new FirefoxDriver();
	String bodyText = "";

	@Before
	public void startSel() {
		// open the website...
		// driver.get("http://visualinteraction.net/PHP/code/project/");
	}

	/**
	 * Common login step defines a user account
	 */

	@Given("^the user enters \"([^\"]*)\" and \"([^\"]*)\"$")
	public void the_user_enters_and(String email, String password) {
		accountEmail = email;
		// grab the username portion of the email
		userName = email.substring(0, email.indexOf('@'));
		accountPassword = password;
	}

	/**
	 * Common login procedure. Requires the login account variables to have been
	 * set in a prior step
	 */
	@When("^the user attempts to login$")
	public void the_user_attempts_to_login() {

		// open the login page...
		driver.get("http://visualinteraction.net/PHP/code/project/login.php");

		WebElement emailElem = driver.findElement(By.name("email"));
		emailElem.sendKeys(accountEmail);
		WebElement passwordElem = driver.findElement(By.name("password"));

		passwordElem.sendKeys(accountPassword);
		// submit the login

		WebElement submitElem = driver.findElement(By.name("submit"));
		submitElem.click();

		// add a wait of up to 10 seconds
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	/**
	 * Step to ensure a successful login. Requires setting account details in a
	 * prior step.
	 */
	@Then("^the user should see a greeting with their username portion of the email$")
	public void the_user_should_see_a_greeting_with_their_username_portion_of_the_email() {
		// in case of a failed login an alert is present, use try/catch to check body text
		try {
			bodyText = driver.findElement(By.tagName("body")).getText();
			assertTrue(bodyText.contains(userName));
		} catch (Exception ex) {
			fail();
		}
		
	}
	/** This step confirms a login failure
	 * 
	 */
	@Then("^the user should not be granted login$")
	public void the_user_should_not_be_granted_login() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			// the assertion
			assertTrue(alertText.contains("invalid"));
		} catch (Exception ex) {
			fail();
		}
		
	}

	/**
	 * This step confirms that the logged in user has access to the authoring
	 * page
	 */
	@Then("^the user should be granted access to the authoring page$")
	public void the_user_should_be_granted_access_to_the_authoring_page() {

		// open the authoring page...
		driver.get("http://visualinteraction.net/PHP/code/project/maintain.php");
		// pause for a few
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// now read the body text
		bodyText = driver.getPageSource();

		assertTrue(bodyText.contains("Authoring Mode"));
	}

	/**
	 * A registration test. Requires account details to be set.
	 */
	@When("^registering for the first time$")
	public void registering_for_the_first_time() {
		// open the registration page...
		driver.get("http://visualinteraction.net/PHP/code/project/register.php");

		WebElement emailElem = driver.findElement(By.name("email"));
		emailElem.sendKeys(accountEmail);

		WebElement passwordElem = driver.findElement(By.name("password"));
		passwordElem.sendKeys(accountPassword);
		// submit the login

		WebElement submitElem = driver.findElement(By.name("submit"));
		submitElem.click();

		// add a wait of up to 10 seconds
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	/**
	 * This step looks for an alert that confirms the user was registered
	 * successfully
	 */
	@Then("^the user should be registered successfully$")
	public void the_user_should_be_registered_successfully() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			// the assertion
			assertTrue(alertText.contains("successfully"));
		} catch (Exception ex) {
			fail();
		}
	}

	/**
	 * This step looks for an alert that lets the user know that registration
	 * was NOT successful
	 */
	@Then("^the user should not be registered$")
	public void the_user_should_not_be_registered() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			// the assertion
			assertTrue(alertText.contains("already registered"));
		} catch (Exception ex) {
			fail();
		}
	}

	/**
	 * This step clicks the logout link; it therefore requires an open browser
	 * window with a signed in user
	 */
	@When("^the user logs out$")
	public void the_user_logs_out() {
		// assumes website is open and user is logged in
		try {
			driver.findElement(By.linkText("Log out...")).click();
			//now grab and close the logout alert..
			Alert alert = driver.switchTo().alert();
			alert.accept();
			// add a wait of up to 10 seconds
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception ex) {
			fail();
		}
	}

	/**
	 * In this step we attempt to access the authoring page. Prerequisite: the
	 * website is assumed to be open
	 */
	@Then("^the user should not be able to access the authoring page$")
	public void the_user_should_not_be_able_to_access_the_authoring_page() {
		// assumes website is open
		try {
			WebElement authorLink = driver.findElement(By.id("lnkReg"));
			authorLink.click();

			// add a wait of up to 10 seconds
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			// now read the body text
			bodyText = driver.getPageSource();

			assertFalse(bodyText.contains("Authoring Mode"));
		} catch (Exception ex) {
			fail();
		}
	}

	// Stop Selenium
	@After
	public void destroySelenium() {
		driver.quit();
	}
}
