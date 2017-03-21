package tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetCurrentDateTime {
	public static String getCurrentDateTime(){
 	   SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");//设置日期格式
 	   return df.format(new Date());
	}
	
	public static String getCurrentHourAndMinTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String time = df.format(new Date());
		String hour = time.substring(9, 11);
		String min = time.substring(11, 13);
		String hAndM = hour+":"+min;
		return hAndM;
	}
	
	public static String getCurrentDayTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String time = df.format(new Date());
		String day = time.substring(0, 9).toString().trim();
		return day;
	}

}
