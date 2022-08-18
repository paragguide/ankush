package utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class ReadXpath {

	public Properties OR = null;
	
	public Properties readxpath(String filename) throws Exception
	{
		 OR = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\"+filename);
		OR.load(fis);
		
		return OR;
	}
}
