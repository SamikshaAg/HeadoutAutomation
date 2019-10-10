package HeadoutAutomation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestBookTickets {
	public static int pageNo, monthSelector=1, day;

	// Function to check if the date entered by user is correct 
  public boolean dateValidation(String date, String format) {
	 if(date == null)
		 return false;
	 
	 SimpleDateFormat sdf = new SimpleDateFormat(format);
	 sdf.setLenient(false);
		try {
			//if not valid, it will throw ParseException
			Date dateToValidate = sdf.parse(date);
			System.out.println(dateToValidate);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
  
  // Test to validate if the advance booking is possible for the entered date
  @Test(priority = 3)
  public void verifyBookingRange() {
	  System.out.println("verifybookingrange");
	  //System.out.println(TestHomePagesVerification.date);
	  boolean isDateValidated = dateValidation(TestHomePagesVerification.date, "dd/MM/yyyy");
	  Assert.assertEquals(true, isDateValidated);
	  
	  String date_dd_MM_yyyy[] = TestHomePagesVerification.date.split("/");
	  int yearDiff = Integer.parseInt(date_dd_MM_yyyy[2])- Calendar.getInstance().get(Calendar.YEAR);
	  
	  Calendar cal = Calendar.getInstance();
	  cal.setTime(new Date(Integer.parseInt(date_dd_MM_yyyy[2]), Integer.parseInt(date_dd_MM_yyyy[1]), Integer.parseInt(date_dd_MM_yyyy[0])));
	  TestBookTickets.day = cal.get(Calendar.DATE);
	  
	  Assert.assertFalse(yearDiff < 0 || yearDiff > 1 , "Year not in range");
	  
	  if(yearDiff == 0) {
		Assert.assertFalse(Integer.parseInt(date_dd_MM_yyyy[1]) < Calendar.getInstance().get(Calendar.MONTH)+1 , "Month not in range");
		TestBookTickets.monthSelector = TestBookTickets.monthSelector + Integer.parseInt(date_dd_MM_yyyy[1]) - (Calendar.getInstance().get(Calendar.MONTH) + 1);
		}
	   else {
		int total_months = (12 - (Calendar.getInstance().get(Calendar.MONTH)+1)) + Integer.parseInt(date_dd_MM_yyyy[1]);
		Assert.assertFalse(total_months > 12, "Month not in range");
			
		TestBookTickets.pageNo = total_months/5;
		//System.out.println(TestBookTickets.pageNo);
		TestBookTickets.monthSelector = total_months+1;
		if(total_months == 12)
			Assert.assertFalse(Integer.parseInt(date_dd_MM_yyyy[0]) < Calendar.getInstance().get(Calendar.DATE), "Date not in range");	
		}
   }
  
  //Function to select the correct month from the navigation bar
 @Test(priority = 4)
 public void selectMonth() throws InterruptedException {
	 Thread.sleep(3000);
	 WebElement nextLink = TestHomePagesVerification.driver.findElement(By.xpath("//div[contains(@id,'NextMonth')]/a"));
		
		while(TestBookTickets.pageNo != 0) {
			nextLink.click();
			TestBookTickets.pageNo--;
		}
		Thread.sleep(3000);	
     WebElement selectedMonth = TestHomePagesVerification.driver.findElement(By.xpath("/html/body/form/div[4]/div[2]/main/div/section/nav/div[1]/div/ul/li["+TestBookTickets.monthSelector+"]/a"));
	selectedMonth.click();

	Thread.sleep(3000);
 }
 
 // Test to redirect to the valid month calendar
 @Test(priority = 5)
 public void verifyShowTime() throws InterruptedException {
	 List<WebElement> selectedTime = TestHomePagesVerification.driver.findElements(By.xpath("//span[contains(text(),'"+TestBookTickets.day+"')]/../..//strong[text()='"+TestHomePagesVerification.showTime+"']"));
	 int length = selectedTime.size();
	 Assert.assertNotEquals(length, 0);
	 Thread.sleep(3000);
	 selectedTime.get(0).click();
 }
 
 //Function to select the time slot
 @Test(priority = 6)
 public void chooseSeats() throws InterruptedException {
	 System.out.println("chooseseat");
	 Thread.sleep(20000);
	 WebElement addToBasket = TestHomePagesVerification.driver.findElement(By.xpath("//div[@class='seat-plan__submit']"));
	 addToBasket.click();
 }
}
