package encryption;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger 
{
	public static void logged(String s)
	{
		Logger logger = Logger.getLogger(MyLogger.class.getName());  
	    FileHandler fh;  

	    try {  

	        // This block configure the logger with handler and formatter 
	    	//File logfile=new File("");
	        //fh = new FileHandler("MyLogFile.log",true);
	        fh=new FileHandler("MyLogFile.log", 1024*50,1,true);
	        
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  

	        // the following statement is used to log any messages  
	       // logger.info(s);
	        logger.log(new LogRecord(Level.INFO,s));

	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  

	   
		
		
		
	}
}
