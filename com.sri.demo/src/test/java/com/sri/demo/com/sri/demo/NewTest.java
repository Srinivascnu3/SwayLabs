package com.sri.demo.com.sri.demo;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NewTest {
	private WebDriver driver;
	
//write By locators for username,password and login button
	public By username=By.id("user-name");
	public By password=By.id("password");
	public By loginButton=By.id("login-button");
	
	public By sortDropdown=By.className("product_sort_container");
	//write by Locator for shopping cart link
	public By shoppingCartLink=By.xpath("//a[@class='shopping_cart_link']");
	
	//checkout
	public By checkoutButton=By.id("checkout");
	
	//first-name last-name postal-code
	//write By locators for first-name,last-name and postal-code
	public By firstName=By.id("first-name");
	public By lastName=By.id("last-name");
	public By postalCode=By.id("postal-code");
	
	public By continueButton=By.id("continue");
	
	By errorMessage=By.xpath("//h3[@data-test='error']");
	
	//summary_subtotal_label
	By subtotalLabel=By.className("summary_subtotal_label");
	
	//finish
	By finishButton=By.id("finish");
	
  @Test
  public void login() {
	   driver.findElement(username).sendKeys("standard_user");
	   driver.findElement(password).sendKeys("secret_sauce");
	   driver.findElement(loginButton).click();
  }
  
  @Test(dependsOnMethods = {"login"})
  public void HomePageTitle() {
	  
		// handle chrome browser popup of change password
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
		} catch (Exception e) {
			System.out.println("No Alert Present");
		}

		String title = driver.getTitle();
		System.out.println("Home Page Title is: " + title);
		
		//product_sort_container
		WebElement filter = driver.findElement(sortDropdown);
		Select s= new Select(filter);
		s.selectByVisibleText("Price (low to high)");
		
		By thirdPrice =By.xpath("(//div[@class='inventory_item'])[3]//div[@class='inventory_item_price']");
		String price =driver.findElement(thirdPrice).getText();
		
		System.out.println("Price of 3rd product is: " + price);
		//write xpath to find the 3rd product on the home page and click on it
		By thirdProduct=By.xpath("(//div[@class='inventory_item'])[3]//button");
		driver.findElement(thirdProduct).click();
		//click on shopping cart link
		driver.findElement(shoppingCartLink).click();
		//click on checkout button
		driver.findElement(checkoutButton).click();
		
		//error message validation
		driver.findElement(continueButton).click();
		
		String errorText=driver.findElement(errorMessage).getText();
		Assert.assertEquals(errorText, "Error: First Name is required");
		
		//enter first-name,last-name and postal-code
		driver.findElement(firstName).sendKeys("John");
		driver.findElement(lastName).sendKeys("Doe");
		driver.findElement(postalCode).sendKeys("605752");
		//click on continue button
		driver.findElement(continueButton).click();
		
		String subtotal=driver.findElement(subtotalLabel).getText();
		Assert.assertTrue(subtotal.contains(price));
		
		//click on finish button
		driver.findElement(finishButton).click();
		String finishMessage=driver.findElement(By.className("complete-header")).getText();
		Assert.assertEquals(finishMessage, "Thank you for your order!");
		
		
		
  }
  
  
  @BeforeClass
  public void beforeMethod() {
	  //initialize the webdriver to chrome browser with options to disable Chrome's password prompts
	  ChromeOptions options = new ChromeOptions();
	  Map<String, Object> prefs = new HashMap<>();
	  prefs.put("credentials_enable_service", false);
	  prefs.put("profile.password_manager_enabled", false);
	  options.setExperimentalOption("prefs", prefs);
	  
	  options.addArguments("--guest");
	  // additional options to try and suppress Chrome UI infobars/password prompts
	  options.addArguments("--disable-notifications");
	  options.addArguments("--disable-infobars");
	  options.addArguments("--disable-extensions");
	  // remove automation flag and disable automation extension
	  options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
	  options.setExperimentalOption("useAutomationExtension", false);
	  /* 🔥 CRITICAL FLAGS 🔥 */
	  options.addArguments("--disable-features=PasswordLeakDetection");
	  options.addArguments("--disable-features=PasswordManagerOnboarding");
	  options.addArguments("--disable-features=PasswordCheck");
	  options.addArguments("--disable-features=SafeBrowsingEnhancedProtection");
	  options.addArguments("--disable-save-password-bubble");
	  
	  
	  driver = new ChromeDriver(options);
	  //open the url
	  driver.get("https://saucedemo.com/");
	  //maximize the window
	  driver.manage().window().maximize();
	  //set the page load timeout
	  driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
	  // small implicit wait
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	  
  }
  
  @AfterClass
  public void afterMethod() {
	  //close the browser
	  driver.quit();
  }
  
  }