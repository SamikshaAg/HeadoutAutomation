package HeadoutAutomation;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
	public static String date, showTime;

//Initiate web driver and take user inputs
 @BeforeSuite
 public void launchBrowser() throws InterruptedException {
	  Scanner sc = new Scanner(System.in);
	  System.out.println("Enter the date in the form of dd/mm/yyyy");
	  date = sc.next();
	  
	  System.out.println("Choose the show timings: 3pm or 7.45pm?");
	  showTime = sc.next();
	  
	  System.setProperty("webdriver.chrome.driver", service);
	  driver = new ChromeDriver();
	  driver.navigate().to(baseURL);
	  driver.manage().window().maximize();
	  Thread.sleep(3000);
 }
 
 //Test to verify if the correct home page is loaded
  @Test(priority = 0)
  public void verifyHomePageURL() {
	  System.out.println("verifyHomePageURL");
	  Assert.assertEquals(baseURL, driver.getCurrentUrl());
  }
  
  // Test to verify if the correct show is loaded
  @Test(priority = 1)
  public void verifyShowTitle() {
	  System.out.println("verifyShowTitle");
	  driver.findElement(By.xpath("//img[@title = 'Mamma Mia!']")).click();
	  String expectedTitle = "Mamma Mia! Tickets - Musical Tickets | London Theatre Direct";
	  String actualTitle = driver.getTitle();
	  Assert.assertEquals(actualTitle, expectedTitle);
  }
  
  //Test to check if the correct booking schedule is loaded
  @Test(priority = 2)
  public void verifyBookingHomePage() {
	  System.out.println("verifyBookingHomePage");
	  driver.findElement(By.xpath("//a[@id='ctl00_MainContent_BookingBoxControl_BookButtonHL']")).click();
	  String actualTitle = driver.getTitle();
	  boolean expectedTitle = actualTitle.contains("Mamma Mia! Tickets");
	  Assert.assertEquals(true, expectedTitle);
  }
  
  //Cleanup activity
  @AfterSuite
  public void terminateBrowser() {
	  driver.close();
  }
}
