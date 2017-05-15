package performance;

import java.util.ArrayList;
import java.util.List;

public class CPUInfo {
	private static float getTotalCPUInfo( String cpuInfo,
			int CpuIndex, String PackageName) {
		float cpu = 0;
		List<String>  dataList = new ArrayList<String>();

		try {
			String data[] = cpuInfo.split("\\s+");
			for (int i = 0; i < data.length; i++) {
				dataList.add(data[i]);
			}

			for (int i = 0; i < dataList.indexOf(PackageName); i++) {
				if (dataList.get(i).toString().contains("%")) {
					cpu = Float.parseFloat((dataList.get(i).toString()
							.substring(0,
									dataList.get(i).toString().length() - 1)));
				}
			}

		} catch (Exception ex) {
			cpu = 0;
		}
		return cpu;

	}


	public static float getCpuData(String sn, String packageName, int CpuIndex) {
		String TOP_CPUINFO = "adb -s "
				+ sn + " shell top | grep " + packageName;
		String cpuInfo = ADBShell.sendADB(TOP_CPUINFO, 5000);

		float cpuData = getTotalCPUInfo(cpuInfo, CpuIndex, packageName);
//		System.out.println(cpuData+"%");
		return cpuData;
	}

}
