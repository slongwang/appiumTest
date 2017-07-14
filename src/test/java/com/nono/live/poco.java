package com.nono.live;

import java.net.URL;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import performance.PhoneInfo;

public class poco {
	private AppiumDriver<AndroidElement> driver;
    private String appPackage = "cn.poco.photo";
    private static int message_probability = 50;
    private static int praise_probability = 20;
    private static int swipe_probability = 30;
    
    @BeforeTest
	@Parameters({ "port", "udid" })
    public void setUp(String port, String udid) throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", udid);
        capabilities.setCapability("platformVersion", PhoneInfo.getPlatformVersion(udid));
        capabilities.setCapability("appPackage", appPackage);
        capabilities.setCapability("newCommandTimeout", "120");
        capabilities.setCapability("noReset", true);
        capabilities.setCapability("unicodeKeyboard", "True");
        capabilities.setCapability("resetKeyboard", "True");
        capabilities.setCapability("appActivity", ".ui.main.FragmentMainActivity");
        driver = new AndroidDriver<AndroidElement>(new URL((new StringBuilder("http://127.0.0.1:")).append(port).append("/wd/hub").toString()), capabilities);
        
    }
    @AfterTest
    public void tearDown() throws Exception
    {
  
        driver.quit();
    }
    
    @Test
    public void testPoco() throws Exception{
    	Thread.sleep(5000);
    	driver.findElements(By.id("cn.poco.photo:id/entrance_item_title")).get(0).click();//works
//    	driver.findElement(By.id("cn.poco.photo:id/activity_photography_to_classification")).click();//sort
//    	try{//portrait
//    		if(driver.findElement(By.id("cn.poco.photo:id/item_classification_recylerview_tv_subtitle")).getText().toString()=="PORTRAIT"){
//    			driver.findElement(By.id("cn.poco.photo:id/item_classification_recylerview_tv_subtitle")).click();
//    		}
//    	}catch (Exception e){
//    		System.out.println("have not find PORTRAIT element");
//    	}
    	Thread.sleep(2000);
    	driver.tap(1, 360, 180, 1);
    	Thread.sleep(2000);
    	//driver.findElements(By.className("android.widget.TextView")).get(3).click();//new
    	probabilityDoSomething();
    }
    
    private void probabilityDoSomething(){
    	try{
    		if(driver.findElement(By.id("cn.poco.photo:id/blog_text_ok")).isDisplayed()){
    			//System.out.println("love button is displayed");
    			//做动作：点赞、评论、滑走、浏览之间选做1个动作
    			probability();
    			swipeUp();
    			Thread.sleep(2000);
    			probabilityDoSomething();
    		}
    	}catch(Exception e){
    		//System.out.println("please check next page");
    		swipeUp();
    		probabilityDoSomething();
    	}
    }
    
    private void praise(){
    	try{
    		driver.findElements(By.id("cn.poco.photo:id/blog_image")).get(0).click();
    		Thread.sleep(2000);
    		driver.findElement(By.id("cn.poco.photo:id/blog_text_ok")).click();
        	driver.findElement(By.id("cn.poco.photo:id/back_btn")).click();
    	}catch (Exception e){
    		System.out.println("praise failure");
    	}
    }
    
    private void sendMessage(){
    	try{
    		driver.findElement(By.id("cn.poco.photo:id/blog_text_appriase")).click();
        	driver.findElement(By.id("cn.poco.photo:id/poco_discuss_editview")).sendKeys(message());
        	driver.tap(1, 680, 1230, 1);//没有send按钮就采取点击发送的方式
        	driver.tap(1, 680, 1230, 1);
        	Thread.sleep(1000);
        	driver.findElement(By.id("cn.poco.photo:id/back_btn")).click();
    	}catch (Exception e){
    		System.out.println("send message failure");
    	}
    	
    	
    	
    }
    
    private String message() //send message
    {
        String Array[] = {
        		"拍得真好，不错","赞","喜欢~，太棒了","很好，有感觉","不错不错，加油！"
        };
        Random r = new Random();
        int i = r.nextInt(Array.length);
        String chat = Array[i];
        return chat;
    }
    
    private void viewSomething(){
    	try{
    		driver.findElements(By.id("cn.poco.photo:id/blog_image")).get(0).click();
    		Thread.sleep(1000);
    		swipeUp();   		
        	driver.findElement(By.id("cn.poco.photo:id/back_btn")).click();
    	}catch (Exception e){
    		System.out.println("praise failure");
    	}
    }
    
    private void swipeUp()//调整参数
    {
        int WIDTH = driver.manage().window().getSize().width;
        int HEIGHT = driver.manage().window().getSize().height;
        driver.swipe(WIDTH / 2, HEIGHT *3 / 5, WIDTH / 2, (HEIGHT) / 5, 1000);
    }
    
    private void probability(){
    	Random r = new Random();
    	int temp = r.nextInt(praise_probability+swipe_probability+message_probability);
    	if(temp<=praise_probability){
    		praise();
    	}else if(temp>praise_probability && temp<=praise_probability+swipe_probability){
    		swipeUp();
    	}else {
    		//viewSomething();
    		sendMessage();
    	}
    	
    }
    
}
