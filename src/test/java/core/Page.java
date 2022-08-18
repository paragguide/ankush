package core;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utility.ReadLog;
import utility.ReadXpath;
import utility.ScreenShot;
import utility.TestConfig;
import utility.monitoringMail;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterTest;

public class Page 
   {
	public WebDriver driver = null;  // global variable
	public ExtentTest test=null;
	public ExtentReports report=null;
	public Logger log  = null;
	public Properties OR = null;
	
  @Parameters({"browser","url","screenshotname"})	
  @BeforeMethod
  public void openBrowser(String browser,String url,String screenshotname) 
  {
	  if(browser.equals("firefox"))
		{
			// register driver
			System.setProperty("webdriver.gecko.driver", "D:\\browserdrivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			
		}
		else if(browser.equals("chrome"))
		{
			 // driver register
			System.setProperty("webdriver.chrome.driver","D:\\browserdrivers\\chromedriver.exe");
			driver = new ChromeDriver();
			
		}
		driver.navigate().to(url);
		
		log.debug("browser opened");
		test.log(LogStatus.INFO, "browser opend");
		
		driver.manage().timeouts().pageLoadTimeout(80L,TimeUnit.SECONDS);
		
		// implicit wait
		driver.manage().timeouts().implicitlyWait(80L, TimeUnit.SECONDS);
		
		driver.manage().window().maximize();
	
		ScreenShot sc = new ScreenShot();
		try {
			sc.getScreenShot(driver, screenshotname);
			
			log.debug("screen shot taken");
			test.log(LogStatus.PASS, "screen shot taken");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }

  @AfterMethod
  public void closeBrowser() 
  {
	  driver.quit();
  }

  @Parameters({"xpathfile"})
  @BeforeClass
  public void getexternalxpath(String xpathfile) 
  {
	  ReadXpath x  = new ReadXpath();
	  try {
		OR = x.readxpath(xpathfile);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }


  @Parameters({"reportname","key"})
  @BeforeTest
  public void createReport(String reportname, String key) 
  {
	  
	  if (!Boolean.parseBoolean(key)) {
		    throw new SkipException("Skipping tests because resource was not available.");
		  }
	  else {
	  
	// extent test initialize
			report = new ExtentReports(System.getProperty("user.dir")+"\\src\\test\\java\\report\\"+reportname+".html");
					  
			test = report.startTest(reportname);
			
			ReadLog r = new ReadLog ();
			try {
			log =	r.generateLog(reportname);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }   // end of else...
  }

  @Parameters({"filename"})
  @AfterTest
  public void endReport(String filename) 
  {
	  // compulsory to end Report
	    report.endTest(test);   // compulsory 
		
		report.flush();  // compulsory
		  
		  String mailpath = System.getProperty("user.dir")+"\\src\\test\\java\\report";
		  String attachmentName = filename;
		  
		  monitoringMail m = new monitoringMail();
		  try {
			m.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, TestConfig.messageBody, mailpath+"\\"+filename , attachmentName);
		System.out.println("sent mail");
		
		log.debug("report generated mail sent");
		test.log(LogStatus.PASS, "mail sent");
		
		  } catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }

}
