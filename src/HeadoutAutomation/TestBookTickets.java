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
	public String date, showTime;
	public int pageNo, monthSelector, day;
	
  @BeforeTest
  public void userInput() {
	  Scanner sc = new Scanner(System.in);
	  System.out.println("Enter the date in the form of dd/mm/yyyy");
	  date = sc.next();
	  
	  System.out.println("Choose the show timings: 3pm or 7.45pm?");
	  showTime = sc.next();
  }
  
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
  
  @Test(priority = 0)
  public void verifyBookingRange() {
	 // System.out.println("Inside Method 1..!!!");
	  boolean isDateValidated = dateValidation(date, "dd/MM/yyyy");
	  Assert.assertEquals(true, isDateValidated);
	  
	  String date_dd_MM_yyyy[] = date.split("/");
	  int yearDiff = Integer.parseInt(date_dd_MM_yyyy[2])- Calendar.getInstance().get(Calendar.YEAR);
	  int pageNo = 0;
	  int monthSelector = 1;
	  
	  Calendar cal = Calendar.getInstance();
	  cal.setTime(new Date(Integer.parseInt(date_dd_MM_yyyy[2]), Integer.parseInt(date_dd_MM_yyyy[1]), Integer.parseInt(date_dd_MM_yyyy[0])));
	  int day = cal.get(Calendar.DATE);
	  
	  Assert.assertFalse(yearDiff < 0 || yearDiff > 1 , "Year not in range");
	  
	  if(yearDiff == 0) {
		Assert.assertFalse(Integer.parseInt(date_dd_MM_yyyy[1]) < Calendar.getInstance().get(Calendar.MONTH)+1 , "Month not in range");
		monthSelector = monthSelector + Integer.parseInt(date_dd_MM_yyyy[1]) - (Calendar.getInstance().get(Calendar.MONTH) + 1);
		}
	   else {
		int total_months = (12 - (Calendar.getInstance().get(Calendar.MONTH)+1)) + Integer.parseInt(date_dd_MM_yyyy[1]);
		Assert.assertFalse(total_months > 12, "Month not in range");
			
		pageNo = total_months/5;
		monthSelector = total_months+1;
		if(total_months == 12)
			Assert.assertFalse(Integer.parseInt(date_dd_MM_yyyy[0]) < Calendar.getInstance().get(Calendar.DATE), "Date not in range");	
		}
   }
  
 @Test(priority = 1)
 public void selectMonth() throws InterruptedException {
  WebElement nextLink = TestHomePagesVerification.driver.findElement(By.xpath("/html/body/form/div[4]/div[2]/main/div/section/nav/div[1]/div/div[2]"));
		while(pageNo != 0) {
			nextLink.click();
			pageNo--;
		}
		
  WebElement selectedMonth = TestHomePagesVerification.driver.findElement(By.xpath("/html/body/form/div[4]/div[2]/main/div/section/nav/div[1]/div/ul/li["+monthSelector+"]/a"));
	Thread.sleep(5000);
	selectedMonth.click();
 }
 
 @Test(priority = 2)
 public void verifyShowTime() throws InterruptedException {
	 List<WebElement> selectedTime = TestHomePagesVerification.driver.findElements(By.xpath("//span[contains(text(),'"+day+"')]/../..//strong[text()='"+showTime+"']"));
	 int length = selectedTime.size();
	 Assert.assertNotEquals(length, 0);
	 
	 Thread.sleep(3000);
	 selectedTime.get(0).click();
 }
 
 @Test(priority = 3)
 public void chooseSeats() {
	 TestHomePagesVerification.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	 WebElement addToBasket = TestHomePagesVerification.driver.findElement(By.xpath("//div[@class='seat-plan__submit']"));
	 addToBasket.click();
 }
}
