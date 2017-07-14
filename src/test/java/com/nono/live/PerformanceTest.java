package com.nono.live;

import java.net.URL;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import performance.PerformanceMonitor;
import performance.PhoneInfo;

public class PerformanceTest {
	private AppiumDriver<AndroidElement> driver;
    private String appPackage = "com.nono.android";
//    private PerformanceMonitor monitor;
    
    @BeforeTest
	@Parameters({ "port", "udid" })
    public void setUp(String port, String udid) throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", udid);
        capabilities.setCapability("platformVersion", "5.0.2");//PhoneInfo.getPlatformVersion(udid)
        capabilities.setCapability("appPackage", appPackage);
        capabilities.setCapability("newCommandTimeout", "120");
        capabilities.setCapability("appActivity", ".modules.splash.SplashActivity");
        capabilities.setCapability("noReset", true);
        driver = new AndroidDriver<AndroidElement>(new URL((new StringBuilder("http://127.0.0.1:")).append(port).append("/wd/hub").toString()), capabilities);
//        monitor = new PerformanceMonitor(udid, appPackage, (new StringBuilder("performanceMonitor")).append(port).toString());
    }
    @AfterTest
    public void tearDown() throws Exception
    {
//    	monitor.stop();
        driver.quit();
    }
    
    @Test
    public void testViewPerformance() throws Exception{
    	Thread.sleep(1000L);
//    	monitor.start();
    	List<AndroidElement> tabs = driver.findElements(By.id("com.nono.android:id/tv_tab_title"));
        ((AndroidElement)tabs.get(1)).click();
        driver.findElements(By.className("android.widget.ImageView")).get(3).click();
        Thread.sleep(1000L);
        for(int i=0;i<60;i++){
	        try{
	        	if(driver.findElement(By.id("com.nono.android:id/live_end_text")).isDisplayed())
	        		swipeDown();
	        }
	    	catch (Exception e){
	    		System.out.println("----- "+i+" -----");
	    	}
	        livetime(60);
        	swipe();
	        }
    }
    @Test
    public void testTimes() throws Exception{
    	Thread.sleep(1000L);
    	try{
	    	driver.findElements(By.className("android.widget.TextView")).get(5).click();
	    	for(int i=0;i<500;i++){
	    		swipe();
	    		System.out.println(i);
	    	}
	    	for(int j=0;j<200;j++){
	    		swipeUp();
	    		Thread.sleep(1000L);
	    		System.out.println(j);
	    	}
	    	for(int k=0;k<200;k++){
	    		swipeDown();
	    		Thread.sleep(2000L);
	    		System.out.println(k);
	    	}
	    	for(int l=0;l<500;l++){
	    		swipe();
	    		Thread.sleep(1000+(int)Math.random()*1000);
	    		System.out.println(l);
	    	}
    	}catch (Exception e){
    		System.out.println("test swipe failure");
    	}
    }
  
    private void livetime(int seconds) 
    {
        for(int i = 0; i < seconds; i++)
        {
            driver.tap(1, 445, 155, 1);
            driver.tap(1, 446, 156, 1);
            driver.tap(1, 447, 157, 1);
        }

    }
    
    private void swipe() throws Exception
    {
    	try{
	        Random r = new Random();
	        int i = r.nextInt(4);
	        if(i == 0)
	            swipeUp();
	        else
	        if(i == 1)
	            swipeDown();
	        else
	        if(i == 2)
	            swipeLeft();
	        else
	        if(i==3)
	            swipeRight();

    	}catch (Exception e)
    	{
    		System.out.println("swip failure");
        	swipeRight();
        	chat();
    	}
    }

    private void swipeLeft()
    {
        int WIDTH = driver.manage().window().getSize().width;
        int HEIGHT = driver.manage().window().getSize().height;
        driver.swipe(WIDTH / 5, HEIGHT / 2, (WIDTH * 4) / 5, HEIGHT / 2, 100);
    }

    private void swipeRight()
    {
        int WIDTH = driver.manage().window().getSize().width;
        int HEIGHT = driver.manage().window().getSize().height;
        driver.swipe((WIDTH * 4) / 5, HEIGHT / 2, WIDTH / 5, HEIGHT / 2, 100);
    }

    private void swipeUp()
    {
        int WIDTH = driver.manage().window().getSize().width;
        int HEIGHT = driver.manage().window().getSize().height;
        driver.swipe(WIDTH / 2, (HEIGHT * 3) / 5, WIDTH / 2, HEIGHT / 5, 100);
    }

    private void swipeDown()
    {
        int WIDTH = driver.manage().window().getSize().width;
        int HEIGHT = driver.manage().window().getSize().height;
        driver.swipe(WIDTH / 2, HEIGHT / 5, WIDTH / 2, (HEIGHT * 3) / 5, 100);
    }
    
    private void chat() throws Exception
    {
        try
        {
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/chat_input_image"))).click();
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/chat_input_edit"))).clear();
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/chat_input_edit"))).sendKeys(new CharSequence[] {
                message()
            });
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/send_chat_btn"))).click();
            System.out.println("send message");
            Reporter.log("chat successed");
            driver.tap(1, 100, 100, 1);
        }
        catch(Exception e)
        {
            System.out.println("chat failed");
            Reporter.log("chat failed");
        }
    }
    
    private String message() 
    {
        String Array[] = {
            "stunning!", "beautiful!", "Good !", " Amazing!", "Awesome!","love you!","sing a song or dance,please!"
        };
        Random r = new Random();
        int i = r.nextInt(Array.length);
        String chat = Array[i];
        return chat;
    }
    
    

}
