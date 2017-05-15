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

public class MomentPerformance {
	private AppiumDriver<AndroidElement> driver;
    private String appPackage = "com.nono.android";
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
        capabilities.setCapability("appActivity", ".modules.splash.SplashActivity");
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
    	Thread.sleep(1000L);
    	monitor.start();
    	try{
    	for(int i=N;i<50;i++){
    		liveSettings();
            Thread.sleep(1000L);
            record();
            System.out.println(i);
            N++;
    	}   
    	}
    	catch(Exception e){
    		System.out.println("test failure,once again");
    		testViewPerformance();
    	}
    }
    
    @Test
    public void testView() throws Exception{
    	Thread.sleep(1000L);
    	monitor.start();
    	driver.findElements(By.id("com.nono.android:id/tv_tab_title")).get(2).click();
    	for(int i=0;i<50;i++){
    		notView();
    		System.out.println(i);
    	}
    }
    
    private void notView() throws Exception{
    	List<AndroidElement> lovers = driver.findElements(By.id("com.nono.android:id/user_name_text"));
    	try{
    		if(existlove()){
    			lovers.get(existloveNumber()).click();
    			try{
	    			if(driver.findElement(By.id("com.nono.android:id/vg")).isDisplayed()){
	    				driver.findElement(By.id("com.nono.android:id/main_bottom_tab_home")).click();
	    				swipeDown();
	    			}}catch (Exception e){
	    				System.out.print("success:");
    			}
    			viewVideo();
    		}
    		else{
    			swipeDown();
    			notView();
    		}
    	}
    	catch (Exception e){
    		System.out.println("hahaha");    		
    	}
    }
    
    private boolean existlove() throws Exception{
    	List<AndroidElement> loves = driver.findElements(By.id("com.nono.android:id/kj"));
    	boolean f = false;
    	for(int i=0;i<loves.size();i++){
    		if(loves.get(i).getText().toString().trim().equals("0")){
    			f=true;
    			break;
    		}
    			
    	}
    	return f;
    }
    
    private int existloveNumber() throws Exception{
    	List<AndroidElement> loves = driver.findElements(By.id("com.nono.android:id/kj"));
    	int f = 0;
    	for(int i=0;i<loves.size();i++){
    		if(loves.get(i).getText().toString().trim().equals("0")){
    			f=i;
    			break;
    		}
    			
    	}
    	return f;
    }
    
    private void viewVideo() throws Exception{
    	try{
    		driver.findElement(By.id("com.nono.android:id/wv")).click();
    		swipeUp();
    		time(30);
    		driver.findElement(By.id("com.nono.android:id/w8")).click();
    	}catch(Exception e){
    		System.out.println("viewVidwo failure");
    	}
    	
    }
    
    private void record() throws Exception{
    	try{
    	driver.findElement(By.id("com.nono.android:id/xv")).click();
    	next();
    	Thread.sleep(1000L);
    	driver.findElement(By.id("com.nono.android:id/ok_btn")).click();
    	confirm();
    	Thread.sleep(1000L);  	
    	}catch(Exception e){
    		System.out.println("record failure");
    	}
    }
    
    private void confirm() throws Exception{
    	try{
    		if(driver.findElement(By.id("com.nono.android:id/go_live_img")).isDisplayed()){
    			Thread.sleep(1000L);
    		}		
    	}catch(Exception e){
    		Thread.sleep(3000L);
    		confirm();
    	}
    }
    private void next() throws Exception{
    	try{    	
    		driver.findElement(By.id("com.nono.android:id/x1")).click();  		
    	}catch (Exception e){
    		Thread.sleep(2000L);
    		next();
    	}
    }
    
    private void liveSettings() throws Exception
    {
        ((AndroidElement)driver.findElement(By.id("com.nono.android:id/go_live_img"))).click();
        ((AndroidElement)driver.findElement(By.id("com.nono.android:id/f2"))).click();
        try
        {
            ((AndroidElement)driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button"))).click();
            ((AndroidElement)driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button"))).click();
            ((AndroidElement)driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button"))).click();
        }
        catch(Exception e)
        {
//            System.out.println("permissions are allowed");
        }
    }
    
    private void time(int seconds) 
    {
    	int WIDTH = driver.manage().window().getSize().width;
        int HEIGHT = driver.manage().window().getSize().height;
        for(int i = 0; i < seconds; i++)
        {
            driver.tap(1, WIDTH / 2, HEIGHT*4/5, 1);
            driver.tap(1, WIDTH / 2, HEIGHT*4/5, 1);
            driver.tap(1, WIDTH / 2, HEIGHT*4/5, 1);
        }

    }
    
    private void swipeUp()
    {
        int WIDTH = driver.manage().window().getSize().width;
        int HEIGHT = driver.manage().window().getSize().height;
        driver.swipe(WIDTH / 2, HEIGHT / 2, WIDTH / 2, HEIGHT / 5, 100);
    }
    
    private void swipeDown()
    {
        int WIDTH = driver.manage().window().getSize().width;
        int HEIGHT = driver.manage().window().getSize().height;
        driver.swipe(WIDTH/2, HEIGHT*5/6, WIDTH/2, HEIGHT/6, 1000);
    }
}
