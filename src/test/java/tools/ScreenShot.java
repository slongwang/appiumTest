package tools;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ScreenShot {
	public static String currentPath = System.getProperty("user.dir")+"\\"+"snapshot";
	public static void snapShot(TakesScreenshot drivername,String palce) throws InterruptedException {
	       snapShot(drivername,GetCurrentDateTime.getCurrentDateTime(),palce);
	    }
		
	public static void snapShot(TakesScreenshot drivername,String filename,String place) throws InterruptedException{
//			Thread.sleep(1000);
	                                                           // folder
	        File scrFile = drivername.getScreenshotAs(OutputType.FILE);        
	        try {
//	            System.out.println("save snapshot path is:" + currentPath + "/"
//	                    + filename);
	            FileUtils.copyFile(scrFile, new File(currentPath + "\\" +place+"\\"+ filename+".png"));
	        } catch (IOException e) {
	            System.out.println("Can't save screenshot");
	            e.printStackTrace();
	        } finally {
//	            System.out.println("screen shot finished, it's in " + currentPath
//	                    + " folder");
	        }   
			
		}

}
