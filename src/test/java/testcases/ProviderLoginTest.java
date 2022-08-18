package testcases;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import core.Page;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;



public class ProviderLoginTest extends Page
{
  @Test(dataProvider = "logindata")
  public void providerlogin(String uid, String pwd, String key) 
  {
	  if (!Boolean.parseBoolean(key)) {
		    throw new SkipException("Skipping tests because resource was not available.");
		  }
	  else {
	  
	  
	  log.debug("test provider login called");
	  test.log(LogStatus.PASS, "provider login test called");
	  
	  String uidxpath = OR.getProperty("uidxpath");
	  String pwdxpath = OR.getProperty("pwdxpath");
	  String submitxpath = OR.getProperty("submitxpath");
	  
	  
	  driver.findElement(By.xpath(uidxpath)).sendKeys(uid);
	  driver.findElement(By.xpath(pwdxpath)).sendKeys(pwd);
	  driver.findElement(By.xpath(submitxpath)).click();
	  
	  log.debug("provider form submitted");
	  test.log(LogStatus.PASS, "provider form submited");
	  
	  }
  }

  @DataProvider
  public Object[][] logindata() throws Exception 
  {
    return utility.POIConfig.getData("C:\\core\\selenium\\Book3.xls", "Sheet1");
  }
}
