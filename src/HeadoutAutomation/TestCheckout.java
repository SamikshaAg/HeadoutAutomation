package HeadoutAutomation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class TestCheckout {
	
  public Properties loadCardDetails() {
	File file = new File("/HeadoutAutomationTest/src/HeadoutAutomation/cardDetails.properties");
	FileInputStream fileInput = null;
	try {
		fileInput = new FileInputStream(file);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	
	Properties prop = new Properties();
	
	try {
		prop.load(fileInput);
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	return prop;
  }
  
  @Test
  public void fillCardDetails() {
	  Properties prop = loadCardDetails();
	  TestHomePagesVerification.driver.findElement(By.xpath("//input[@placeholder = 'John Doe']")).sendKeys(prop.getProperty("nameOnCard"));
	  TestHomePagesVerification.driver.findElement(By.xpath("//input[@placeholder = 'john@doe.com']")).sendKeys(prop.getProperty("userEmail"));
	  TestHomePagesVerification.driver.findElement(By.xpath("//input[@placeholder = '0333 700 8800']")).sendKeys(prop.getProperty("telephone"));
	  TestHomePagesVerification.driver.findElement(By.xpath("//input[@name = 'postcode']")).sendKeys(prop.getProperty("zipCode"));
	  TestHomePagesVerification.driver.findElement(By.xpath("//input[@name = 'city']")).sendKeys(prop.getProperty("city"));
	  TestHomePagesVerification.driver.findElement(By.xpath("//input[@name = 'address']")).sendKeys(prop.getProperty("address"));
	  TestHomePagesVerification.driver.findElement(By.xpath("//input[@autocomplete = 'cc-number']")).sendKeys(prop.getProperty("ccNumber"));
	  TestHomePagesVerification.driver.findElement(By.xpath("//input[@autocomplete = 'cc-exp']")).sendKeys(prop.getProperty("ccExpiry"));
	  TestHomePagesVerification.driver.findElement(By.xpath("//input[@autocomplete = 'cc-csc']")).sendKeys(prop.getProperty("cvv"));
  }
}
