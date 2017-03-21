package performance;

public class FlowInfo {
	private static float[] getTotalFlowInfo(String sn, String flowInfo) {
		float[] flowData = new float[3];
		float receiveData ,sendData = 0;
		
		String data[] = flowInfo.split("\\s+");		
		String receiveInfo = data[2];
		String sendInfo = data[10];
		receiveInfo = receiveInfo.trim();
		sendInfo = sendInfo.trim();
		receiveData = Float.parseFloat(receiveInfo);
		sendData = Float.parseFloat(sendInfo);
		flowData[0] = receiveData/(1024*1024);
		flowData[1] = sendData/(1024*1024);
		flowData[2] = receiveData +sendData;
		flowData[2] = flowData[2]/(1024*1024);
		return flowData;

	}

	public static float[] getFlowData(String sn, String packageName) {
		String FlowInfo = "adb -s "+sn+
				 " shell cat /proc/" + getPidInfo(sn,packageName)+"/net/dev"+"|grep wlan0";	
		String flowInfo = ADBShell.sendADB(FlowInfo, 5000);		
		float flowData[] = new float[3];
		flowData = getTotalFlowInfo(sn, flowInfo);
		return flowData;
	}
	
	public static String getPid(String Pidinfo){
		String data[] = Pidinfo.split("\\s+");
		return data[1];		
	}
	
	public static String getPidInfo(String sn,String packageName){
		String PidDate =null;
		String Pidinfo = ADBShell.sendADB("D:\\Android\\sdk\\platform-tools\\adb -s"+sn
				+" shell ps |grep "+packageName, 5000);
		PidDate = getPid(Pidinfo);
		
		PidDate=PidDate.trim();
		return PidDate;
		
	}

}
