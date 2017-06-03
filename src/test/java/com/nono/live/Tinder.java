package com.nono.live;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import performance.PhoneInfo;

public class Tinder {
	private AppiumDriver<AndroidElement> driver;
    private String appPackage = "com.tinder";
    public static final String END = "bye";
    @BeforeTest
	@Parameters({ "port", "udid" })
    public void setUp(String port, String udid) throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", udid);
        capabilities.setCapability("platformVersion", PhoneInfo.getPlatformVersion(udid));
        capabilities.setCapability("appPackage", appPackage);
        capabilities.setCapability("newCommandTimeout", "120");
        capabilities.setCapability("appActivity", ".activities.ActivityMain");
        driver = new AndroidDriver<AndroidElement>(new URL((new StringBuilder("http://127.0.0.1:")).append(port).append("/wd/hub").toString()), capabilities);
        }
    
    @AfterTest
    public void tearDown() throws Exception
    {
        driver.quit();
    }
    
    @Test
    public void testlike() throws Exception{
    	Thread.sleep(8000);
    	for(int i=0;i<100;i++){		
    		String age = driver.findElement(By.id("com.tinder:id/txt_age")).getText().toString().trim();
    		int ages = Integer.parseInt(age.substring(2));
    		if (ages<35){
    			driver.findElement(By.id("com.tinder:id/revised_like")).click();
    			try{
        			if(driver.findElement(By.id("com.tinder:id/matched_text")).isDisplayed()){
        				driver.findElement(By.id("com.tinder:id/btn_start_chatting")).click();
        				sendHello();
        				Thread.sleep(2000);
        			}
        			System.out.println("matched！");
        			
        		}catch(Exception e){
        			System.out.println("unmatch！");
        		}
    		}   				   		
    		else
    			driver.findElement(By.id("com.tinder:id/revised_pass")).click();
    		Thread.sleep(2000);
    		
    	}
    	
    	
    	
    }
    
    @Test
    public void testchat() throws Exception{
    	Thread.sleep(8000);
    	driver.findElement(By.id("com.tinder:id/tab_matches")).click();
    	Thread.sleep(1000);
//    	newMatched();
    	unviewCheckAndChat();
    }
    
    @Test
    public void testExplore() throws Exception{
    	Thread.sleep(8000);
    	driver.findElement(By.id("com.tinder:id/tab_matches")).click();
    	Thread.sleep(2000L);
    	for(int i=0;i<100;i++){
    		printMessage();
    		swipeDown();
    	}
    }
        
    private void printMessage() throws Exception{
    	List <AndroidElement> matchers = driver.findElements(By.id("com.tinder:id/avatar_view_single"));
    	for(int i=0;i<matchers.size();i++){
    		matchers.get(i).click();
    		Thread.sleep(2000L);
    		getName();
    		getMessages();
    		driver.findElement(By.className("android.widget.ImageButton")).click();
    		
    	}
    }
    
    private void getMessages() throws Exception{
    	List <AndroidElement> messages = driver.findElements(By.id("com.tinder:id/textView_message"));
    	for(int i=0;i<messages.size();i++){
    		System.out.println(messages.get(i).getText().toString().trim());
    	}
    }
    private void getName() throws Exception{
    	System.out.println(" ");
    	System.out.println("Name:"+driver.findElement(By.id("com.tinder:id/txt_name")).getText().toString().trim());
    	System.out.println("Messages:");
    }
    private String sayHello() throws Exception{
   	 	String Array[] = {
   	            "Halo,apa kabar ?","Hello","how r u?"
   	        };
   	        Random r = new Random();
   	        int i = r.nextInt(Array.length);
   	        String hello = Array[i];
   	        return hello;
   }
    
   private void sendHello() throws Exception{
	   try{
			driver.findElement(By.id("com.tinder:id/conversation_message_editText")).click();
			driver.findElement(By.id("com.tinder:id/conversation_message_editText")).sendKeys(sayHello());
			driver.findElement(By.id("com.tinder:id/button_send")).click();
			driver.findElement(By.className("android.widget.ImageButton")).click();
			Thread.sleep(1000L);
   	}catch (Exception e){
   		System.out.println("chat failure！");
   	}
   }
    
  
   private void newMatched() throws Exception{
	   try{
		   String newMatchCount = driver.findElement(By.id("com.tinder:id/matches_new_matches_count")).getText().toString().trim();
		   int mCount = Integer.parseInt(newMatchCount);
		   for(int i=0;i<mCount;i++){
			   try{
				   driver.findElements(By.id("com.tinder:id/avatar_view_single")).get(0).click();
				   sendHello();			   
				   }catch (Exception e){
					   System.out.println("no new matched now");
				   }
			   Thread.sleep(2000L);
		   }
	   }catch (Exception e){
		   System.out.println("have not new matched");
	   }
	   
	   
   }  
   
   private void unviewCheckAndChat() throws Exception{
	   try{		 		   
		   if(redPoint()){		   
				 //没有发现红点就下滑一页
				   swipeDown();
				   unviewCheckAndChat();		   
			   }		   
			   else{
				 //点击红点并聊天，并在此判断是否存在红点
				   driver.findElements(By.id("com.tinder:id/imageView_unviewed_indicator")).get(0).click();
				   chatLogic();
				   driver.findElement(By.className("android.widget.ImageButton")).click();
				   unviewCheckAndChat();
			   }
	   }catch (Exception e){
		   System.out.println("hahahahaha");
	   }
	   
		   
		   
   }
   
   private boolean redPoint() throws Exception{
	   boolean flag =false;
	   try{
		   flag=driver.findElement(By.id("com.tinder:id/imageView_unviewed_indicator")).isDisplayed();
		   flag=false;
	   }catch(Exception e){
		   flag=true;
	   }
	   return flag;
   }

   private String getMessage() throws Exception{
	   List<AndroidElement> messages = driver.findElements(By.id("com.tinder:id/textView_message"));
	   String lastmessage = messages.get(messages.size()-1).getText().toString().trim();
	   return lastmessage;
   }
   
   private void chat(String message) throws Exception{
	   driver.findElement(By.id("com.tinder:id/conversation_message_editText")).click();
	   driver.findElement(By.id("com.tinder:id/conversation_message_editText")).sendKeys(message);
	   driver.findElement(By.id("com.tinder:id/button_send")).click();
   }
  
   
   private void swipeDown()
   {
       int WIDTH = driver.manage().window().getSize().width;
       int HEIGHT = driver.manage().window().getSize().height;
       driver.swipe(WIDTH/2, HEIGHT*5/6, WIDTH*2/3, HEIGHT/6, 1000);
   }
   
   public void chatLogic() throws Exception{
   	String exp="";
   	int j=0;
   	for(int i=0;i<10;i++){
   		String input = getMessage();
   		if (END.equalsIgnoreCase(input)) {
               break;
               }
   		System.out.println(input);
   		System.out.println(exp);
   		if(input.equals(exp)){
   			Thread.sleep(5000L);
   			j++;
   			System.out.println("not receive new chat message "+j+" times");    			
   			if(j==8){
   				break;
   			}
   		}else{
   			exp=json2str(getUrl(getMessage()));
   			chat(exp);
   		}
   		if(i==9){
   			chat("Aku biasanya live di Nonolive loh, ID Nonolive aku 326411. Kita bisa ngobrol langsung disana. Pasti lebih seru.");
   			chat("nonolive http://m.onelink.me/431f8460");
   		}
   	}
   }
   
        
   private String getUrl(String input) throws Exception{
	   	String result = "";
       BufferedReader in = null;
       try {
       	String param = URLEncoder.encode(input ,"utf-8");
           String urlNameString ="http://52.77.95.9:8299/get_response"+"?"+"user_input=" + param;
           System.out.println(urlNameString);
           URL realUrl = new URL(urlNameString);
           // 打开和URL之间的连接
           URLConnection connection = realUrl.openConnection();
           // 设置通用的请求属性
           connection.setRequestProperty("accept", "*/*");
           connection.setRequestProperty("connection", "Keep-Alive");
           connection.setRequestProperty("user-agent",
                   "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
           // 建立实际的连接
           connection.connect();
           // 获取所有响应头字段
           Map<String, List<String>> map = connection.getHeaderFields();
           // 遍历所有的响应头字段
           for (String key : map.keySet()) {
               System.out.println(key + "--->" + map.get(key));
           }
           // 定义 BufferedReader输入流来读取URL的响应
           in = new BufferedReader(new InputStreamReader(
                   connection.getInputStream()));
           String line;
           while ((line = in.readLine()) != null) {
               result += line;
           }
       } catch (Exception e) {
           System.out.println("发送GET请求出现异常！" + e);
           e.printStackTrace();
       }
       // 使用finally块来关闭输入流
       finally {
           try {
               if (in != null) {
                   in.close();
               }
           } catch (Exception e2) {
               e2.printStackTrace();
           }
       }
       return result;
   }
   
   private String json2str(String jsonresult) throws Exception{
   	JsonParser parser=new JsonParser();  //创建JSON解析器
       JsonObject object=(JsonObject) parser.parse(jsonresult);  //创建JsonObject对象
       System.out.println(object.get("response").getAsString());
       return object.get("response").getAsString();
       
   }


}
