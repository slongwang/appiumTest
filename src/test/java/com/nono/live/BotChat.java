package com.nono.live;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bitoflife.chatterbean.AliceBotMother;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import performance.PhoneInfo;

public class BotChat {
	private AppiumDriver<AndroidElement> driver;
    private String appPackage = "com.nono.android";
//    private PerformanceMonitor monitor;
    private AliceBotMother mother;   	   	
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
        capabilities.setCapability("appActivity", ".modules.splash.SplashActivity");
        driver = new AndroidDriver<AndroidElement>(new URL((new StringBuilder("http://127.0.0.1:")).append(port).append("/wd/hub").toString()), capabilities);
//        monitor = new PerformanceMonitor(udid, appPackage, (new StringBuilder("performanceMonitor")).append(port).toString());
        mother = new AliceBotMother();
    }
    @AfterTest
    public void tearDown() throws Exception
    {
//    	monitor.stop();
        driver.quit();
    }
    
    @Test
    public void testChat() throws Exception{
    	
    	Thread.sleep(2000L);
    	//进入聊天页
    	driver.findElement(By.id("com.nono.android:id/main_bottom_tab_me")).click();
    	driver.findElement(By.id("com.nono.android:id/us")).click();
    	driver.findElement(By.id("com.nono.android:id/fd")).click();   	
    	driver.findElements(By.id("com.nono.android:id/j1")).get(0).click();
    	Thread.sleep(6000L);
//    	botChange();
    	chatLogic();
    	
    	
    }
    
    @Test
    public void testExport() throws Exception{
    	
    }
    
    private void chatLogic() throws Exception{
    	String exp="";
    	int j=0;
    	for(int i=0;i<10;i++){
    		String input = getMessage();
    		if (END.equalsIgnoreCase(input)) {
                break;
                }
    		if(input.equals(exp)){
    			Thread.sleep(5000L);
    			j++;
    			System.out.println("not receive new chat message "+j+" times");    			
    			if(j==9){
    				break;
    			}
    		}else{
    			exp=json2str(getUrl(getMessage()));
    			chatMessage(input);
    		}
    		if(i==9){
    			chat("I have to working");
    			chat("You can come and watch my live");
    		}
    	}
    }
    
    private void chatMessage(String message) throws Exception{
    	String respone = getUrl(message);
    	String chats = json2str(respone);
    	chat(chats);
    }
    public void chat(String message) throws Exception{//发送消息
    	driver.findElement(By.id("com.nono.android:id/chat_input_edit")).click();
    	driver.findElement(By.id("com.nono.android:id/chat_input_edit")).clear();
    	driver.findElement(By.id("com.nono.android:id/chat_input_edit")).sendKeys(message);
    	driver.findElement(By.id("com.nono.android:id/send_chat_btn")).click();
    	Thread.sleep(2000L);
    }
    
    public String getMessage() throws Exception{//获取消息
    	List<AndroidElement> messages = driver.findElements(By.id("com.nono.android:id/ya"));
    	System.out.println(messages.get(messages.size()-1).getText().toString().trim());
    	return messages.get(messages.size()-1).getText().toString().trim();
    }
    
//    public void botChange() throws Exception{    	
//    	System.getProperties().load(BotChat.class.getClassLoader().getResourceAsStream("my.properties"));    	  	
//    	mother.setUp(); 
//        AliceBot bot = mother.newInstance();
//        while (true) {   
//            if (END.equalsIgnoreCase(getMessage())) {
//                break;
//            }
//            chat(bot.respond(getMessage()));
//            Thread.sleep(8000L);
//        }
        
//    }
    
    
    private String getUrl(String input) throws Exception{
 	   	String result = "";
        BufferedReader in = null;
        try {
        	String param = URLEncoder.encode(input ,"utf-8");
            String urlNameString ="http://192.168.0.144:8000/get_response"+"?"+"user_input=" + param;
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
