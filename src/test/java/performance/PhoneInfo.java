package performance;

public class PhoneInfo {
	
	public static String getBrandInfo(String sn){//获取手机品牌
		String BRANDINFO = "D:\\Android\\sdk\\platform-tools\\adb -s "
				+sn+ " shell getprop "+"ro.product.brand";
		String brandInfo = ADBShell.sendADB(BRANDINFO, 5000);
		brandInfo=brandInfo.trim();
		return brandInfo;
	}
	
	public static String getPhoneModelInfo(String sn){//获取手机型号
		String PHONEMODELINFO = "D:\\Android\\sdk\\platform-tools\\adb -s "
				+sn+ " shell getprop "+"ro.product.model";
		String phoneModelInfo = ADBShell.sendADB(PHONEMODELINFO, 5000);
		phoneModelInfo = phoneModelInfo.trim();
		return phoneModelInfo;
	}
	
	public static String getSNInfo(String sn){//获取手机sn号
		String SNINFO = "D:\\Android\\sdk\\platform-tools\\adb -s "
				+sn+ " shell getprop "+"ro.serialno";
		String snInfo = ADBShell.sendADB(SNINFO, 5000);
		snInfo=snInfo.trim();
		return snInfo;
	}
	
	public static String getPlatformVersion(String sn){//获取手机版本号
		String PVINFO = "D:\\Android\\sdk\\platform-tools\\adb -s "
				+sn+ " shell getprop "+"ro.build.version.release";
		String versionInfo = ADBShell.sendADB(PVINFO, 5000);
		versionInfo = versionInfo.trim();
		return versionInfo;
	}
	
	public static String getHeapGrowthLimit(String sn){//获取应用分配内存大小		
		String HGLINFO = "D:\\Android\\sdk\\platform-tools\\adb -s "
				+sn+ " shell getprop "+"dalvik.vm.heapgrowthlimit";
		String heapGrowthLimitInfo = ADBShell.sendADB(HGLINFO, 5000);
		heapGrowthLimitInfo = heapGrowthLimitInfo.trim();
		return heapGrowthLimitInfo;
	}
	
	public static String getHeapSize(String sn){//获取应用最大分配内存大小		
		String HSINFO = "D:\\Android\\sdk\\platform-tools\\adb -s "
				+sn+ " shell getprop "+"dalvik.vm.heapsize";
		String heapSizeInfo = ADBShell.sendADB(HSINFO, 5000);
		heapSizeInfo = heapSizeInfo.trim();
		return heapSizeInfo;
	}
	
	public static String getDPI(String sn){//获取设备DPI信息
		String DPIINFO = "D:\\Android\\sdk\\platform-tools\\adb -s "
				+sn+ " shell getprop "+"ro.sf.lcd_density";
		String dpiInfo = ADBShell.sendADB(DPIINFO, 5000);
		dpiInfo = dpiInfo.trim();
		return dpiInfo;
	}
	
	public static String getResolution(String sn){//获取设备分辨率信息
		String RSINFO = "D:\\Android\\sdk\\platform-tools\\adb -s"
				+sn+ " shell dumpsys window displays|grep init";
		String rsData = ADBShell.sendADB(RSINFO, 5000);
		String data[] = rsData.split("\\s+");
		String rsInfo = data[4].substring(4).trim();
		return rsInfo;
	}
	
}
