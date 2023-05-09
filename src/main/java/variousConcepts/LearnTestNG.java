package variousConcepts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

	public class LearnTestNG {
	WebDriver driver;

	@BeforeMethod
	public void init() {
		System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		
		driver.get("https://www.techfios.com/billing/?ng=admin/");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(6000, TimeUnit.MILLISECONDS);	
	}
	@Test
	public void loginTestUsingTestNG() {

		Assert.assertEquals(driver.getTitle(), "Login - iBilling", "You have Landed a Worng page");

//These are call Element Library
		WebElement USER_NAME_ELEMENT = driver.findElement(By.xpath("//input[@id=\'username\']"));
		WebElement PASSWORD_ELEMENT = driver.findElement(By.xpath("//input[@type='password']"));
		WebElement SUBMIT_BUTTON_ELEMENT = driver.findElement(By.xpath("//button[@type='submit']"));
//Login Data:
		String loginID = "demo@techfios.com";
		String password = "abc123";
			
		USER_NAME_ELEMENT.sendKeys(loginID);
		PASSWORD_ELEMENT.sendKeys(password);
		SUBMIT_BUTTON_ELEMENT.click();
		
		WebElement DASHBOARD_BUTTON_ELEMENT = driver.findElement(By.xpath("//h2[contains(text(),'Dashboard ')]"));
		Assert.assertEquals(DASHBOARD_BUTTON_ELEMENT.getText(), "Dashboard", "You have landed WRONG Page !!!");
	}
	
	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();	
	}



}
