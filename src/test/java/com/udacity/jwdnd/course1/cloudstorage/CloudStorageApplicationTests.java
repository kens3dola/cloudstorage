package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	private HomePage homePage;

	private NotePage notePage;

	private CredPage credPage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	void testHomePageNotAccessibleWithoutLoggingIn(){
		driver.get(getUrl()+"/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	void testSignUp(){
		doMockSignUp("a", "b", "c", "d");
		doLogIn("c", "d");
		Assertions.assertEquals("Home", driver.getTitle());
		WebElement logoutBtn = driver.findElement(By.id("logout"));
		logoutBtn.click();
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get(getUrl()+"/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	void testNoteFunction(){
		// signup and login
		doMockSignUp("a","b","c","d");
		doLogIn("c", "d");

		// add new note
		this.homePage = new HomePage(this.driver);
		this.notePage = new NotePage(this.driver);
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		this.homePage.addNewNote("note title", "note description");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
		WebElement message = driver.findElement(By.id("message"));
		Assertions.assertEquals("Added new note", message.getText());
		Assertions.assertTrue(this.driver.getPageSource().contains("note title"));
		Assertions.assertTrue(this.driver.getPageSource().contains("note description"));

		// re-login
		this.homePage.logout();
		Assertions.assertEquals("Login", driver.getTitle());
		doLogIn("c", "d");
		Assertions.assertEquals("Home", driver.getTitle());

		// view a note
		this.homePage.viewNote(0);
		Assertions.assertEquals("My Note", driver.getTitle());

		// update note
		Assertions.assertEquals("note title", this.notePage.getTitle().getAttribute("value"));
		Assertions.assertEquals("note description", this.notePage.getDescription().getAttribute("value"));
		this.notePage.updateNote("update title", "update description");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
		message = driver.findElement(By.id("message"));
		Assertions.assertEquals("Note updated", message.getText());

		Assertions.assertTrue(this.driver.getPageSource().contains("update title"));
		Assertions.assertTrue(this.driver.getPageSource().contains("update description"));

		// delete note
		this.homePage.deleteNote(0);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
		message = driver.findElement(By.id("message"));
		Assertions.assertTrue(message.getText().contains("Deleted"));
		Assertions.assertFalse(this.driver.getPageSource().contains("update description"));
	}

	@Test
	void testCredentialFunction(){
		// signup and login
		doMockSignUp("a","b","c","d");
		doLogIn("c", "d");

		// add new note
		this.homePage = new HomePage(this.driver);
		this.credPage = new CredPage(this.driver);
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		this.homePage.addNewCred("test url", "test username", "test password");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
		WebElement message = driver.findElement(By.id("message"));
		Assertions.assertEquals("Added new credential", message.getText());
		Assertions.assertTrue(this.driver.getPageSource().contains("test url"));
		Assertions.assertTrue(this.driver.getPageSource().contains("test username"));
		Assertions.assertFalse(this.driver.getPageSource().contains("test password"));

		// re-login
		this.homePage.logout();
		Assertions.assertEquals("Login", driver.getTitle());
		doLogIn("c", "d");
		Assertions.assertEquals("Home", driver.getTitle());

		// view a cred
		this.homePage.viewCred(0);
		Assertions.assertEquals("My Credential", driver.getTitle());

		// update cred
		Assertions.assertEquals("test url", this.credPage.getUrl().getAttribute("value"));
		Assertions.assertEquals("test username", this.credPage.getUsername().getAttribute("value"));
		Assertions.assertEquals("test password", this.credPage.getPassword().getAttribute("value"));
		this.credPage.updateCred("update url", "update username", "update password");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
		message = driver.findElement(By.id("message"));
		Assertions.assertEquals("Credential updated", message.getText());

		Assertions.assertTrue(this.driver.getPageSource().contains("update url"));
		Assertions.assertTrue(this.driver.getPageSource().contains("update username"));
		Assertions.assertFalse(this.driver.getPageSource().contains("update password"));

		// delete note
		this.homePage.deleteCred(0);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
		message = driver.findElement(By.id("message"));
		Assertions.assertTrue(message.getText().contains("Deleted"));
		Assertions.assertFalse(this.driver.getPageSource().contains("update username"));
	}

	private String getUrl(){
		return "http://localhost:"+this.port;
	}
}
