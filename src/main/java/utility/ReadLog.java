package utility;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ReadLog {

	Logger log  = null;
	
	public Logger generateLog(String name) throws Exception
	{
		Properties l = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\"+name+".properties");
		l.load(fis);
		PropertyConfigurator.configure(l);  // load
		
		log = log.getLogger(name);
		
return log;
	}

}
