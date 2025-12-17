package com.sri.demo.com.sri.demo;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NewTest {
	private WebDriver driver;
	
//write By locators for username,password and login button
	public By username=By.id("user-name");
	public By password=By.id("password");
	public By loginButton=By.id("login-button");
	
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
		
		By thirdPrice =By.xpath("(//div[@class='inventory_item'])[3]//div[@class='inventory_item_price']");
		String price =driver.findElement(thirdPrice).getText();
		
		System.out.println("Price of 3rd product is: " + price);
		//write xpath to find the 3rd product on the home page and click on it
		By thirdProduct=By.xpath("(//div[@class='inventory_item'])[3]//button");
		driver.findElement(thirdProduct).click();

  }
  
  
  @BeforeClass
  public void beforeMethod() {
	  //initialize the webdriver to chrome browser with options to disable Chrome's password prompts
	  ChromeOptions options = new ChromeOptions();
	  Map<String, Object> prefs = new HashMap<>();
	  prefs.put("credentials_enable_service", false);
	  prefs.put("profile.password_manager_enabled", false);
	  options.setExperimentalOption("prefs", prefs);
	  // additional options to try and suppress Chrome UI infobars/password prompts
	  options.addArguments("--disable-notifications");
	  options.addArguments("--disable-infobars");
	  options.addArguments("--disable-extensions");
	  // remove automation flag and disable automation extension
	  options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
	  options.setExperimentalOption("useAutomationExtension", false);
	  
	  
	  
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
	  //driver.quit();
  }
  
  }