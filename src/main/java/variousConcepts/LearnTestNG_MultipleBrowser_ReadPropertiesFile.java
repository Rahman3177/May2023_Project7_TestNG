package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LearnTestNG_MultipleBrowser_ReadPropertiesFile {
	WebDriver driver;
	String browser = null;
	String url_123;

	@BeforeClass
	public void readConfigFile() {
		// These are the 4 Calsses to read any kinds of
		// file:BufferedReader/InputStream/FileReader?Scanner
		Properties prop = new Properties();
		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used : " + browser);
			url_123 = prop.getProperty("url");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeMethod
	public void init() {
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();

		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

//		driver.get("https://www.techfios.com/billing/?ng=admin/");
		driver.get(url_123);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(6000, TimeUnit.MILLISECONDS);
	}

//	@Test (priority=1)
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
		Assert.assertEquals(DASHBOARD_BUTTON_ELEMENT.getText(), "Dashboard", "Landed Right Page");
	}

	@Test(priority = 2)
	public void addCustomer() {

		Assert.assertEquals(driver.getTitle(), "Login - iBilling", "You have Landed a Worng page");

//These are call Element Library
		WebElement USER_NAME_ELEMENT = driver.findElement(By.xpath("//input[@id=\'username\']"));
		WebElement PASSWORD_ELEMENT = driver.findElement(By.xpath("//input[@type='password']"));
		WebElement SUBMIT_BUTTON_ELEMENT = driver.findElement(By.xpath("//button[@type='submit']"));
//Login Data:
		String loginID = "demo@techfios.com";
		String password = "abc123";

//Test Data or Mock Data:
		String fullName = "Luthfor Rahman";
		String companyName = "Apple";
		String emailAddress = "luthfor.r@gmail.com";
		String phoneNumber = "123-456-7890";
		String addressName = "3001 Communications Pkwy";
		String cityName = "Plano";
		String stateName = "Texas";

		USER_NAME_ELEMENT.sendKeys(loginID);
		PASSWORD_ELEMENT.sendKeys(password);
		SUBMIT_BUTTON_ELEMENT.click();

		WebElement DASHBOARD_BUTTON_ELEMENT = driver.findElement(By.xpath("//h2[contains(text(),'Dashboard ')]"));
		Assert.assertEquals(DASHBOARD_BUTTON_ELEMENT.getText(), "Dashboard", "Landed Right Page");

		WebElement CUSTOMERS_ELEMENT = driver.findElement(By.xpath("//span[contains(text(),'Customers')]"));
		WebElement ADD_CUSTOMERS_ELEMENT = driver.findElement(By.xpath("//*[@id='side-menu']/li[3]/ul/li[1]/a"));

		CUSTOMERS_ELEMENT.click();
		ADD_CUSTOMERS_ELEMENT.click();

		WebElement FULLNAME_ELEMENT = driver.findElement(By.xpath("//input[@id='account']"));
		WebElement COMPANY_DROPDOWN_ELEMENT = driver.findElement(By.xpath("//select[@id='cid']"));
		WebElement EMAIL_ELEMENT = driver.findElement(By.xpath("//input[@id='email']"));
		WebElement PHONE_ELEMENT = driver.findElement(By.xpath("//input[@name='phone']"));
		WebElement ADDRESS_ELEMENT = driver.findElement(By.xpath("//input[@id='address']"));
		WebElement CITY_ELEMENT = driver.findElement(By.xpath("//input[@id='city']"));

// Here we are Using EXPLICIT wait sending "City name"...cuz if driver unable to find WebElement then this will help...or if there Exception like "NoSuchElementFound"
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(CITY_ELEMENT));

		WebElement STATE_ELEMENT = driver.findElement(By.xpath("//input[@name='state']"));

		FULLNAME_ELEMENT.sendKeys(fullName);
//DropDown...here we need to create an object for comapny DropDown
		Select sel = new Select(COMPANY_DROPDOWN_ELEMENT);
		sel.selectByVisibleText(companyName);

//Generate RANDOM Number
		Random rnd = new Random();
		int randomNum = rnd.nextInt(999);

		EMAIL_ELEMENT.sendKeys(randomNum + emailAddress);
		PHONE_ELEMENT.sendKeys(phoneNumber);
		ADDRESS_ELEMENT.sendKeys(addressName);
		CITY_ELEMENT.sendKeys(cityName);
		STATE_ELEMENT.sendKeys(stateName);

	}

//	@AfterMethod
	public void tearDown() {

		driver.close();
		driver.quit();
	}
}
