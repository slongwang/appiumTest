package com.nono.live;

import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import performance.PerformanceMonitor;
import performance.PhoneInfo;

public class kwaiTest {
	private AppiumDriver<AndroidElement> driver;
    private String appPackage = "com.smile.gifmaker";
    private PerformanceMonitor monitor;
    private int N=0;
    
    @BeforeTest
	@Parameters({ "port", "udid" })
    public void setUp(String port, String udid) throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", udid);
        capabilities.setCapability("platformVersion", PhoneInfo.getPlatformVersion(udid));
        capabilities.setCapability("appPackage", appPackage);
        capabilities.setCapability("newCommandTimeout", "120");
        capabilities.setCapability("appActivity", "com.yxcorp.gifshow.HomeActivity");
        driver = new AndroidDriver<AndroidElement>(new URL((new StringBuilder("http://127.0.0.1:")).append(port).append("/wd/hub").toString()), capabilities);
        monitor = new PerformanceMonitor(udid, appPackage, (new StringBuilder("performanceMonitor")).append(port).toString());
    }
    @AfterTest
    public void tearDown() throws Exception
    {
    	monitor.stop();
        driver.quit();
    }
    
    @Test
    public void testViewPerformance() throws Exception{
    	Thread.sleep(8000L);
    	monitor.start();
    	try{
	    	for(int i=N;i<100;i++){
	    		viewList();
	    		swipeDown();
	    		System.out.println(i);
	    		N++;
	    		Thread.sleep(1000L);
	    	}
    	}catch (Exception e){
    		System.out.println("test failure,once again");
    		testViewPerformance();
    	}
    }
    
    private void viewList() throws Exception{
    	try{
    		List<AndroidElement> videos = driver.findElements(By.id("com.smile.gifmaker:id/avatar"));
        	for(int i=0;i<videos.size();i++){
        		videos.get(i).click();
        		viewVideo();
        		Thread.sleep(1000L);
        	}
    	}catch (Exception e){
    		System.out.println("hahaha");
    	}
    	
    }
    private void viewVideo() throws Exception{   	
    	try{
    		if(driver.findElement(By.id("com.smile.gifmaker:id/created")).isDisplayed()){
    			time(5);
//    			driver.findElement(By.id("com.smile.gifmaker:id/like_button")).click();
    	    	Thread.sleep(1000L);
    			driver.findElement(By.id("com.smile.gifmaker:id/back_btn")).click();
    		}
    	}catch(Exception e){
    		Thread.sleep(5000L);
    		driver.findElement(By.id("com.smile.gifmaker:id/like_button_white")).click();
        	Thread.sleep(1000L);
    		driver.findElement(By.id("com.smile.gifmaker:id/back_btn_white")).click();
    	}  	
    }
    
	private void time(int seconds) 
    {
        for(int i = 0; i < seconds; i++)
        {           
            driver.findElement(By.id("com.smile.gifmaker:id/created")).click();
        }

    }
    
    private void swipeUp()
    {
        int WIDTH = driver.manage().window().getSize().width;
        int HEIGHT = driver.manage().window().getSize().height;
        driver.swipe(WIDTH/2, HEIGHT/2, WIDTH/2, HEIGHT/3, 1);
    }
    
    private void swipeDown()
    {
        int WIDTH = driver.manage().window().getSize().width;
        int HEIGHT = driver.manage().window().getSize().height;
        driver.swipe(WIDTH/2, HEIGHT*5/6, WIDTH/2, HEIGHT/6, 1000);
    }
}
