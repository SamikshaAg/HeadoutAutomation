package HeadoutAutomation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class TestCheckout {
	
	//Function to create the fileInput object for the properties file
  public FileInputStream loadCardDetails() {
	File file = new File("C:\\Users\\samiagar\\eclipse-workspace\\HeadoutAutomationTest\\src\\HeadoutAutomation\\cardDetails.properties");
	FileInputStream fileInput = null;
	try {
		fileInput = new FileInputStream(file);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	
	return fileInput;
  }
  
  //Function to agree to the terms and conditions checkbox
  @Test(priority = 7)
  public void agreeToTerms() throws InterruptedException {
	  System.out.println("agree to terms");
	  Thread.sleep(10000);
	  WebElement terms = TestHomePagesVerification.driver.findElement(By.xpath("//strong[contains(text(),'I agree to Terms')]/../.."));
	  terms.click();
	  TestHomePagesVerification.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }
  
  //Function to select the credit card as payment mode and fill out the details
  @Test(priority = 8)
  public void fillCardDetails() throws InterruptedException {
	  System.out.println("fillcard details");
	  WebElement paymentMode = TestHomePagesVerification.driver.findElement(By.xpath("//strong[text()='Credit or Debit Card']/../.."));
	  paymentMode.click();
	  
	  Properties prop = new Properties();
	  try {
			prop.load(loadCardDetails());
		} catch (IOException e) {
			e.printStackTrace();
		}

	  TestHomePagesVerification.driver.findElement(By.xpath("//input[@placeholder = 'John Doe']")).sendKeys(prop.getProperty("nameOnCard"));
	  TestHomePagesVerification.driver.findElement(By.xpath("//input[@placeholder = 'john@doe.com']")).sendKeys(prop.getProperty("userEmail"));
	  TestHomePagesVerification.driver.findElement(By.xpath("//input[@placeholder = '0333 700 8800']")).sendKeys(prop.getProperty("telephone"));
	  TestHomePagesVerification.driver.findElement(By.xpath("//input[@name = 'postcode']")).sendKeys(prop.getProperty("zipCode"));
	  TestHomePagesVerification.driver.findElement(By.xpath("//input[@name = 'city']")).sendKeys(prop.getProperty("city"));
	  TestHomePagesVerification.driver.findElement(By.xpath("//input[@name = 'address']")).sendKeys(prop.getProperty("address"));
	  TestHomePagesVerification.driver.findElement(By.xpath("//input[@autocomplete = 'cc-number']")).sendKeys(prop.getProperty("ccNumber"));
	  TestHomePagesVerification.driver.findElement(By.xpath("//input[@autocomplete = 'cc-exp']")).sendKeys(prop.getProperty("ccExpiry"));
	  TestHomePagesVerification.driver.findElement(By.xpath("//input[@autocomplete = 'cc-csc']")).sendKeys(prop.getProperty("cvv"));
	  
	  Thread.sleep(5000);
  }
  
  //Function to finish the booking
  @Test(priority = 9)
  public void finishBooking() throws InterruptedException {
	  System.out.println("finish booking");
	  TestHomePagesVerification.driver.findElement(By.xpath("//div[@class = 'my--3']//child::button")).click();
	  
	  Thread.sleep(5000);
  }
}
