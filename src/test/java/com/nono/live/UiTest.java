package com.nono.live;

import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
import tools.ScreenShot;

public class UiTest {
    private AppiumDriver<AndroidElement> driver;
    private String appPackage = "com.nono.android";
    private PerformanceMonitor monitor;
    
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
    
    @Parameters({ "udid" })
	@Test(priority = 1) 
    public void phoneInfomation(String udid) throws Exception
    {
        try
        {
            Reporter.log("Phone Information:");
            Reporter.log((new StringBuilder("Model:")).append(PhoneInfo.getBrandInfo(udid)).append(" ").append(PhoneInfo.getPhoneModelInfo(udid)).toString());
            Reporter.log((new StringBuilder("Resolution:")).append(PhoneInfo.getResolution(udid)).toString());
            Reporter.log((new StringBuilder("DPI:")).append(PhoneInfo.getDPI(udid)).toString());
            monitor.start();
        }
        catch(Exception e)
        {
            Reporter.log("Get phone information failure");
        }
    }
    @Parameters({ "storage" })
	@Test(priority = 10) 
    public void homepageHot(String sn) throws InterruptedException
    {
        try
        {
            Thread.sleep(1000L);
            Thread.sleep(1000L);
            checkIn(sn);
            checkViewList(sn);
            sendGift(sn);
            share();
            chat();
            Thread.sleep(2000L);
            textfly();
            Thread.sleep(2000L);
            HDChange(sn);
            follow();
            unfollowed();
            checkView(sn);
            Reporter.log("Test Hot page successed!");
        }
        catch(Exception e)
        {
            Reporter.log("homepageHot test failed");
            monitor.stop();
        }
    }

    private void checkViewList(String sn) throws InterruptedException
    {
        try
        {
            ScreenShot.snapShot(driver, "live", sn);
            List<AndroidElement> texts = driver.findElements(By.className("android.widget.TextView"));
            String nickname = ((AndroidElement)texts.get(3)).getText().toString();
            String viewerNumber = ((AndroidElement)texts.get(5)).getText().toString();
            int viewListNumber = Integer.parseInt(viewerNumber);
            ((AndroidElement)texts.get(3)).click();
            Thread.sleep(2000L);
            ScreenShot.snapShot(driver, "hot", sn);
            try
            {
                for(int i = 0; i < 10; i++)
                    if(((AndroidElement)driver.findElement(By.id("com.nono.android:id/live_end_text"))).getText().toString() == "Live Ended")
                        swipeDown();

                System.out.println("not find living room in 10 times");
                Reporter.log("not find living room in 10 times");
            }
            catch(Exception e)
            {
                System.out.println("enter living room");
                Reporter.log("enter living room");
            }
            List<AndroidElement> infos = driver.findElements(By.className("android.widget.TextView"));
            if(((AndroidElement)infos.get(0)).getText().toString().equals(nickname))
            {
                System.out.println("nickname checked successed");
                Reporter.log("nickname test successed");
            }
            int viewInsideNuber = Integer.parseInt(((AndroidElement)infos.get(1)).getText().toString());
            int diffViewers = viewListNumber - viewInsideNuber;
            if(diffViewers > 200 || diffViewers < -200)
            {
                System.out.println("viewers number different greatly!");
                System.out.println((new StringBuilder("viewlist Number is:")).append(viewListNumber).toString());
                System.out.println((new StringBuilder("viewers InsideNuber number is:")).append(viewInsideNuber).toString());
                Reporter.log("viewers number diff big!");
                Reporter.log((new StringBuilder("viewlist Number is:")).append(viewListNumber).toString());
                Reporter.log((new StringBuilder("viewers InsideNuber number is:")).append(viewInsideNuber).toString());
            } else
            {
                System.out.println("viewers number diff small ,test success");
                Reporter.log("viewers number diff small ,test success");
            }
        }
        catch(Exception e)
        {
            System.out.println("check viewlist failed");
        }
    }

    private void checkView(String sn) throws Exception
    {
        for(int i = 0; i < 10; i++)
        {
            swipe();
            Thread.sleep(1000L);
        }

        swipeRight();
        ((AndroidElement)driver.findElement(By.id("com.nono.android:id/close_btn"))).click();
        Thread.sleep(2000L);
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

    private void textfly() throws Exception
    {
        try
        {
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/chat_input_image"))).click();
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/danmu_toggle_btn"))).click();
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/chat_input_edit"))).clear();
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/chat_input_edit"))).sendKeys(new CharSequence[] {
                message()
            });
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/send_chat_btn"))).click();
            System.out.println("send textfly");
            try
            {
                ((AndroidElement)driver.findElement(By.id("android:id/button2"))).click();
                System.out.println("coins not enough");
            }
            catch(Exception e)
            {
                System.out.println("textfly send successed");
            }
            driver.tap(1, 50, 50, 1);
        }
        catch(Exception e)
        {
            System.out.println("textfly failed");
            Reporter.log("textfly failed");
        }
    }

    private void share() throws InterruptedException
    {
        try
        {
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/share_btn"))).click();
            List<AndroidElement> texts = driver.findElementsByClassName("android.widget.ImageView");
            ((AndroidElement)texts.get(texts.size() - 8)).click();
            try
            {
                if(((AndroidElement)driver.findElement(By.className("android.widget.EditText"))).isDisplayed())
                {
                    ((AndroidElement)driver.findElement(By.id("com.nono.android:id/share_btn"))).click();
                    Thread.sleep(2000L);
                    System.out.println("shared via facebook successed");
                    Reporter.log("shared  via facebook successed");
                } else
                {
                    System.out.println("facebook app is not isntall");
                }
            }
            catch(Exception e)
            {
                System.out.println("share via facebook failed");
                Reporter.log("share  via facebook failed");
            }
        }
        catch(Exception e)
        {
            System.out.println("share failed");
            Reporter.log("share failed");
        }
    }

    private void sendGift(String sn) throws InterruptedException
    {
        try
        {
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/send_gift_btn"))).click();
            ScreenShot.snapShot(driver, "sendGift", sn);
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/ne"))).click();
            Thread.sleep(3000L);
            try
            {
                cancel();
                System.out.println("coins not enough");
                Reporter.log("coins not enough");
            }
            catch(Exception e)
            {
                System.out.println("coins enough");
                Reporter.log("coins enough");
            }
            System.out.println("send gift successed");
            Reporter.log("send gift successed");
            driver.tap(1, 50, 50, 1);
        }
        catch(Exception e)
        {
            System.out.println("send gift failed");
        }
    }

    private void HDChange(String sn) throws InterruptedException
    {
        try
        {
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/videomodel_btn"))).click();
            List<AndroidElement> texts = driver.findElementsByClassName("android.widget.TextView");
            if(((AndroidElement)texts.get(texts.size() - 3)).getText().toString().equals("Please choose the live mode"))
                System.out.println("click HD button successed");
            ((AndroidElement)texts.get(texts.size() - 1)).click();
            System.out.println("changed to Low model");
            Thread.sleep(2000L);
            ScreenShot.snapShot(driver, "Low", sn);
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/videomodel_btn"))).click();
            ((AndroidElement)texts.get(texts.size() - 2)).click();
            System.out.println("changed to HD model");
            Thread.sleep(2000L);
            ScreenShot.snapShot(driver, "HD", sn);
            System.out.println("changed HD model successed");
        }
        catch(Exception e)
        {
            System.out.println("HD change failed");
            Reporter.log("HD change failed");
        }
    }

    private void checkIn(String sn) throws InterruptedException
    {
        Thread.sleep(3000L);
        try
        {
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/check_in_open_image"))).click();
            Thread.sleep(1000L);
            ScreenShot.snapShot(driver, "checkin", sn);
            List<AndroidElement> texts = driver.findElements(By.className("android.widget.TextView"));
            String checkin = ((AndroidElement)texts.get(texts.size() - 1)).getText().toString();
            System.out.println(checkin);
            if(checkin.equals("Check in everyday and win more Exp points!"))
            {
                System.out.println("check in successed!");
                Reporter.log("check in successed!");
            } else
            {
                System.out.println("checking in!");
                Reporter.log("check in successed!");
            }
            ((AndroidElement)driver.findElements(By.id("com.nono.android:id/tv_tab_title")).get(1)).click();
        }
        catch(Exception e)
        {
            System.out.println("today had checked!");
            Reporter.log("today had checked!");
        }
    }
    @Parameters({ "storage" })
	@Test(priority = 11)
    public void homepageExplore(String sn)  throws InterruptedException
    {
        try
        {
            System.out.println("Start Test explore page...");
            List<AndroidElement> tabs = driver.findElements(By.id("com.nono.android:id/tv_tab_title"));
            ((AndroidElement)tabs.get(2)).click();
            search();
            Thread.sleep(2000L);
            enterCountryList();
            Thread.sleep(2000L);
            checkExploreList(sn);
            Reporter.log("Test Explore page complete!");
        }
        catch(Exception e)
        {
            Reporter.log("Test Explore page failure!");
            monitor.stop();
        }
    }

    private void search() throws Exception
    {
        try
        {
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/search_btn"))).click();
            ((AndroidElement)driver.findElement(By.className("android.widget.EditText"))).clear();
            ((AndroidElement)driver.findElement(By.className("android.widget.EditText"))).sendKeys(new CharSequence[] {
                "2461652"
            });
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/search_btn"))).click();
            String username = ((AndroidElement)driver.findElement(By.id("com.nono.android:id/user_name_text"))).getText().toString().trim();
            if(username.equals("lion"))
            {
                System.out.println("Test search by id success");
                Reporter.log("Test search by id success");
            } else
            {
                System.out.println("Test search by id failure");
                Reporter.log("Test search by id failure");
            }
            ((AndroidElement)driver.findElement(By.className("android.widget.EditText"))).clear();
            ((AndroidElement)driver.findElement(By.className("android.widget.EditText"))).sendKeys(new CharSequence[] {
                "lion"
            });
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/search_btn"))).click();
            List<AndroidElement> names = driver.findElements(By.id("com.nono.android:id/user_name_text"));
            for(int i = 0; i < names.size(); i++)
            {
                String name = ((AndroidElement)names.get(i)).getText().toString().trim();
                if(!name.equals("lion"))
                    continue;
                System.out.println("Test search by username success");
                Reporter.log("Test search by username success");
                break;
            }

            ((AndroidElement)driver.findElement(By.className("android.widget.ImageButton"))).click();
        }
        catch(Exception e)
        {
            Reporter.log("Test search failure");
        }
    }

    private void enterCountryList() throws Exception
    {
        try
        {
            List<AndroidElement> countries = driver.findElements(By.id("com.nono.android:id/explore_country_item"));
            for(int i = 0; i < countries.size() - 1; i++)
            {
                ((AndroidElement)countries.get(i)).click();
                ((AndroidElement)driver.findElements(By.className("android.widget.ImageView")).get(0)).click();
                try
                {
                    ((AndroidElement)driver.findElement(By.id("com.nono.android:id/close_btn"))).click();
                    back();
                }
                catch(Exception e)
                {
                    System.out.println("broadcaster is not in live");
                }
            }

            System.out.println("Test enter country list success");
            Reporter.log("Test enter country list success");
        }
        catch(Exception e)
        {
            Reporter.log("Test Explore country list failure");
        }
    }

    private void checkExploreList(String sn) throws Exception
    {
        ((AndroidElement)driver.findElement(By.id("com.nono.android:id/live_state_img"))).click();
        checkView(sn);
        System.out.println("test explore list successed");
        Reporter.log("test explore list successed");
    }
    @Parameters({ "storage" })
	@Test(priority = 12)
    public void homepageFollow(String sn) throws Exception
    {
        try
        {
            Reporter.log("start test Follow page  ...");
            System.out.println("start test Follow page ...");
            List<AndroidElement> tabs = driver.findElements(By.id("com.nono.android:id/tv_tab_title"));
            ((AndroidElement)tabs.get(0)).click();
            checkViewList(sn);
            unfollowed();
            follow();
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/close_btn"))).click();
            Thread.sleep(2000L);
            Reporter.log("test Follow page success");
        }
        catch(Exception e)
        {
            Reporter.log("test Follow page failure");
            monitor.stop();
        }
    }

    private void unfollowed() throws Exception
    {
        try
        {
            List<AndroidElement> images = driver.findElementsByClassName("android.widget.ImageView");
            ((AndroidElement)images.get(0)).click();
            List<AndroidElement> texts = driver.findElementsByClassName("android.widget.TextView");
            String followStatus = ((AndroidElement)texts.get(texts.size() - 1)).getText().toString();
            if(followStatus.equals("Followed"))
            {
                System.out.println("follow status is followed");
                ((AndroidElement)texts.get(texts.size() - 1)).click();
                ((AndroidElement)driver.findElement(By.id("android:id/button1"))).click();
                if(((AndroidElement)driver.findElement(By.id("com.nono.android:id/follow_state"))).isDisplayed())
                    System.out.println("unfollowed successed");
            } else
            {
                System.out.println("follow status is unfollow");
            }
        }
        catch(Exception e)
        {
            System.out.println("unfollow failure");
        }
    }

    private void follow() throws Exception
    {
        try
        {
            AndroidElement followButton = (AndroidElement)driver.findElement(By.id("com.nono.android:id/follow_state"));
            if(followButton.isDisplayed())
                followButton.click();
            try
            {
                if(followButton.isDisplayed())
                    System.out.println("follow failed");
                else
                    System.out.println("follow successed");
            }
            catch(Exception e)
            {
                System.out.println("follow successed");
            }
        }
        catch(Exception e)
        {
            System.out.println("has followed!");
        }
    }
    
    @Parameters({ "storage" })
	@Test(priority = 30)
    public void me() throws Exception
    {
        checkMeInfo();
//        logout();
    }

    private void checkMeInfo() throws Exception
    {
        try
        {
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/main_bottom_tab_me"))).click();
            String nickname = ((AndroidElement)driver.findElement(By.id("com.nono.android:id/user_name_text"))).getText().toString().trim();
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/user_name_text"))).click();
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/username_item"))).click();
            ((AndroidElement)driver.findElement(By.className("android.widget.EditText"))).clear();
            ((AndroidElement)driver.findElement(By.className("android.widget.EditText"))).sendKeys(new CharSequence[] {
                "newNickName"
            });
            ((AndroidElement)driver.findElements(By.className("android.widget.TextView")).get(1)).click();
            ((AndroidElement)driver.findElement(By.className("android.widget.ImageButton"))).click();
            String newnickname = ((AndroidElement)driver.findElement(By.id("com.nono.android:id/user_name_text"))).getText().toString().trim();
            if(newnickname.equals("newNickName"))
            {
                System.out.println("change nickname success");
                Reporter.log("change nickname success");
            }
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/user_name_text"))).click();
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/username_item"))).click();
            ((AndroidElement)driver.findElement(By.className("android.widget.EditText"))).clear();
            ((AndroidElement)driver.findElement(By.className("android.widget.EditText"))).sendKeys(new CharSequence[] {
                nickname
            });
            ((AndroidElement)driver.findElements(By.className("android.widget.TextView")).get(1)).click();
            ((AndroidElement)driver.findElement(By.className("android.widget.ImageButton"))).click();
            System.out.println("rechange nickname success");
        }
        catch(Exception e)
        {
            System.out.println("Test change nickname failure");
            Reporter.log("Test change nickname failure");
            monitor.stop();
        }
    }

    private void logout() throws Exception
    {
        swipeUp();
        ((AndroidElement)driver.findElement(By.id("com.nono.android:id/setting_item"))).click();
        ((AndroidElement)driver.findElement(By.id("com.nono.android:id/logout_item"))).click();
        ((AndroidElement)driver.findElement(By.id("android:id/button1"))).click();
    }
    @Parameters({ "storage" })
	@Test(priority = 20)
    public void goLive() throws Exception
    {
        try
        {
            Reporter.log("start test golive ...");
            liveSettings();
            Thread.sleep(2000L);
            checkLive();
            livetime(100);
            ((AndroidElement)driver.findElement(By.id("com.nono.android:id/close_btn"))).click();
            confirm();
            ((AndroidElement)driver.findElement(By.className("android.widget.Button"))).click();
            Reporter.log("Test go live success");
        }
        catch(Exception e)
        {
            Reporter.log("Test go live failure");
            monitor.stop();
        }
    }

    private void liveSettings() throws Exception
    {
        ((AndroidElement)driver.findElement(By.id("com.nono.android:id/go_live_img"))).click();
        ((AndroidElement)driver.findElement(By.className("android.widget.Button"))).click();
        try
        {
            ((AndroidElement)driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button"))).click();
            ((AndroidElement)driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button"))).click();
            ((AndroidElement)driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button"))).click();
        }
        catch(Exception e)
        {
            System.out.println("permissions are allowed");
        }
    }

    private void checkLive() throws Exception
    {
    }

    private void livetime(int seconds) 
    {
        for(int i = 0; i < seconds; i++)
        {
            driver.tap(1, 445, 555, 1);
            driver.tap(1, 446, 556, 1);
            driver.tap(1, 447, 557, 1);
        }

    }
    @Parameters({ "storage" })
	@Test(priority = 40)
    public void phoneNumberLogin() throws InterruptedException
    {
        Thread.sleep(3000L);
        List<AndroidElement> countrys = driver.findElements(By.className("android.widget.ImageView"));
        ((AndroidElement)countrys.get(4)).click();
        Thread.sleep(500L);
        ((AndroidElement)driver.findElement(By.xpath("//android.widget.LinearLayout/android.widget.ImageView[4]"))).click();
        Thread.sleep(500L);
        ((AndroidElement)driver.findElement(By.xpath("//android.widget.TextView[@text = 'Login']"))).click();
        List<AndroidElement> usernameAndPassword = driver.findElements(By.className("android.widget.EditText"));
        ((AndroidElement)usernameAndPassword.get(0)).clear();
        ((AndroidElement)usernameAndPassword.get(0)).sendKeys(new CharSequence[] {
            "13800138005"
        });
        ((AndroidElement)usernameAndPassword.get(1)).clear();
        ((AndroidElement)usernameAndPassword.get(1)).sendKeys(new CharSequence[] {
            "123456"
        });
        ((AndroidElement)driver.findElement(By.xpath("//android.widget.Button"))).click();
        driver.manage().timeouts().implicitlyWait(20L, TimeUnit.SECONDS);
        Reporter.log("test phonenumber Login successed!");
    }

    private String message() 
    {
        String Array[] = {
            "stunning!", "beautiful post! well done!", "Good post! i am inspired.", " Amazing! I like it.", "Awesome! so good. "
        };
        Random r = new Random();
        int i = r.nextInt(Array.length);
        String chat = Array[i];
        return chat;
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
	            swipeRight();
    	}catch (Exception e)
    	{
    		System.out.println("swip failure");
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

    private void back() throws Exception
    {
        try
        {
            ((AndroidElement)driver.findElement(By.className("android.widget.ImageButton"))).click();
        }
        catch(Exception e)
        {
            ((AndroidElement)driver.findElement(By.className("android.widget.ImageView"))).click();
        }
    }

    private void confirm() throws Exception
    {
        ((AndroidElement)driver.findElement(By.id("android:id/button1"))).click();
    }

    private void cancel() throws Exception
    {
        ((AndroidElement)driver.findElement(By.id("android:id/button2"))).click();
    }

}
