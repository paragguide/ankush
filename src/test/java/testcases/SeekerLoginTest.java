package testcases;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import core.Page;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;

public class SeekerLoginTest extends Page
{
  
	
  @Test(dataProvider = "seekerdata")
  public void seekerlogin(String n, String s) 
  {
	  log.debug("test login called");
	  test.log(LogStatus.PASS, "login test called");
	  
	  String uidxpath = OR.getProperty("uidxpath");
	  String pwdxpath = OR.getProperty("pwdxpath");
	  String submitxpath = OR.getProperty("submitxpath");
	  
	  
	  driver.findElement(By.xpath(uidxpath)).sendKeys(n);
	  driver.findElement(By.xpath(pwdxpath)).sendKeys(s);
	  driver.findElement(By.xpath(submitxpath)).click();
	  
	  log.debug("seeker form submitted");
	  test.log(LogStatus.PASS, "seeker form submited");
  }

  @DataProvider
  public Object[][] seekerdata() throws Exception 
  {
	  return utility.POIConfig.getData("C:\\core\\selenium\\Book3.xls", "Sheet3");
  }
  
}
