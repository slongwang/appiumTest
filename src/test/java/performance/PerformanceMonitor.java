package performance;


import java.util.HashMap;
import tools.GetCurrentDateTime;

public class PerformanceMonitor implements Runnable{	
	private String DeviceID ="";
	private String AppName ="";
	private boolean isRunning = true;
	private boolean suspend = false;
	private Thread t;
	private String threadName;
	private static HashMap<String, String> resultList = new HashMap<String, String>();
	public PerformanceMonitor(String DeviceID,String AppName,String threadName){
		this.DeviceID = DeviceID;
		this.AppName =AppName;
		this.threadName= threadName;
	}
	
	public void start(){
		if(t == null){
			this.isRunning = true;
			t = new Thread(this,threadName);		
			t.start();
		}
	}
	public synchronized void resume(){
		this.suspend = false;
		notifyAll();
	}
	public  void suspend(){
		this.suspend = true;
	}
	
	public void stop(){
		this.isRunning =false;
	}
		
	@Override
	public synchronized void run(){
		float[] fflow = FlowInfo.getFlowData(this.DeviceID, this.AppName);
		while (this.isRunning) {
			if(this.suspend){	
				 try
		            {
		               // 若线程挂起则阻塞自己
		             wait();
		            }
		            catch (InterruptedException e)
		            {
		               System.out.println("线程异常终止...");
		            }
			}
			else{
				String time = tools.GetCurrentDateTime.getCurrentHourAndMinTime();
				resultList.put("Time",time);
				float cpuinfo= CPUInfo.getCpuData(this.DeviceID, this.AppName, 3);
				resultList.put("CPU",cpuinfo+"%");
				float meminfo = MEMInfo.getPssData(this.DeviceID, this.AppName);
				meminfo = meminfo/1024;
				meminfo = (float)(Math.round(meminfo*100))/100;
				resultList.put("MEM", meminfo+"");
				float[] flow = FlowInfo.getFlowData(this.DeviceID, this.AppName);
				float[] flows = new float[3];
				for(int i = 0;i<3;i++){
					flows[i] =flow[i]-fflow[i];
					flows[i] = (float)(Math.round(flows[i]*100))/100;
				}
				resultList.put("FLOWReceive", flows[0]+"");
				resultList.put("FLOWSend", flows[1]+"");
				resultList.put("FLOWTotal", flows[2]+"");
				try {
					Thread.sleep(3000);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				FileUtils.writeFile(GetCurrentDateTime.getCurrentDayTime()+PhoneInfo.getSNInfo(this.DeviceID), JsonUtil.toJson(resultList)+"\n", true);
		
			}
			
		}
	}
	
}
