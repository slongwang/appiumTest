package performance;

public class FPSInfo {
	private static float[] getTotalFpsInfo(String sn, String FpsInfo) {
		float[] Fps = new float[10];
		int LastExcute = 0;
		try {
			String data[] = FpsInfo.split("\\s+");
			for(int k = 0;k<data.length;k++){
				if(data[k].equals("Execute"))
					LastExcute=k;
			}
			System.out.println("最后一个execute在数组中的位置"+LastExcute);
			for(int i=0;i<10;i++){
				Fps[i]=Float.parseFloat(data[i*4+LastExcute+1])+Float.parseFloat(data[i*4+LastExcute+8])+Float.parseFloat(data[i*4+LastExcute+9]);
				Fps[i]=(float)(Math.round(Fps[i]*100))/100;
				System.out.println(Fps[i]);
			}
					
		} catch (Exception ex) {
			Fps = null;
		}
		return Fps;
		

	}

	public static float[] getFpsData(String sn, String packageName) throws InterruptedException {
		String FPSINFO = "adb -s "
				+ sn + " shell dumpsys gfxinfo " + packageName + "| grep -A60 Process ";
		String FPSCLEAR = "adb -s "
				+ sn + " shell dumpsys gfxinfo " + packageName + "reset ";
		ADBShell.sendADB(FPSCLEAR, 5000);
		Thread.sleep(5000);
		String fpsInfo = ADBShell.sendADB(FPSINFO, 5000);
		System.out.println(fpsInfo);
		float fpsData[] = new float[10];
		fpsData= getTotalFpsInfo(sn, fpsInfo);
		//
		return fpsData;
	}
	
}
