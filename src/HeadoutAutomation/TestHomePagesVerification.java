package HeadoutAutomation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestHomePagesVerification {
	String service = "C:\\Users\\samiagar\\AppData\\Roaming\\npm\\node_modules\\chromedriver\\lib\\chromedriver\\chromedriver.exe";
	String baseURL = "https://www.londontheatredirect.com/";
	public static WebDriver driver;
	
 @BeforeSuite
 public void launchBrowser() throws InterruptedException {
	 System.setProperty("webdriver.chrome.driver", service);
	  driver = new ChromeDriver();
	  driver.navigate().to(baseURL);
	  driver.manage().window().maximize();
	  Thread.sleep(3000);
 }
 
  @Test(priority = 0)
  public void verifyHomePageURL() {
	  Assert.assertEquals(baseURL, driver.getCurrentUrl());
  }
  
  @Test(priority = 1)
  public void verifyShowTitle() {
	  driver.findElement(By.xpath("//img[@title = 'Mamma Mia!']")).click();
	  String expectedTitle = "Mamma Mia! Tickets - Musical Tickets | London Theatre Direct";
	  String actualTitle = driver.getTitle();
	  Assert.assertEquals(actualTitle, expectedTitle);
  }
  
  @Test(priority = 2)
  public void verifyBookingHomePage() {
	  driver.findElement(By.xpath("//a[@id='ctl00_MainContent_BookingBoxControl_BookButtonHL']")).click();
	  String actualTitle = driver.getTitle();
	  boolean expectedTitle = actualTitle.contains("Mamma Mia! Tickets");
	  Assert.assertEquals(true, expectedTitle);
  }
  
  @AfterSuite
  public void terminateBrowser() {
	  driver.close();
  }
}
