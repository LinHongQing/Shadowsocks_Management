package utils;

import java.io.File;
import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

import bean.SystemInfoBean;

public class SystemInfoUtil {

	public static String getDiskInfo() {
		StringBuffer sb = new StringBuffer();
		File[] roots = File.listRoots();// 获取磁盘分区列表
		int i = 0;
		for (File file : roots) {
			i++;
			long totalSpace = file.getTotalSpace();
			long usableSpace = file.getUsableSpace();
			if (totalSpace > 0) {
				sb.append(file.getPath() + "[All: ");
				sb.append(Math.round(((double) totalSpace / (1024 * 1024 * 1024)) * 100 / 100.0) + " GB ");
				if (Math.round((((double) usableSpace / (1024 * 1024 * 1024)) * 100) / 100.0) <= 1) {
					sb.append("Free: " + Math.round((((double) usableSpace / (1024 * 1024)) * 100) / 100.0) + " MB]");
				} else {
					sb.append("Free: " + Math.round((((double) usableSpace / (1024 * 1024 * 1024)) * 100) / 100.0) + " GB]");
				}
				if (i != roots.length)
					sb.append(" ");
			}
		}
		return sb.toString();
	}

	public static String getEMS() {
		StringBuffer sb = new StringBuffer();
		OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		sb.append("系统名称：" + osmb.getName() + "\n");
		sb.append("系统版本：" + osmb.getVersion() + "\n");
		sb.append("系统体系结构：" + osmb.getArch() + "\n");
		sb.append("系统CPU负载：" + osmb.getSystemCpuLoad() + "\n");
		sb.append("系统平均负载：" + osmb.getSystemLoadAverage() + "\n");
		sb.append("进程CPU负载：" + osmb.getProcessCpuLoad() + "\n");
		sb.append("进程CPU时间：" + osmb.getProcessCpuTime() / 1000 / 1000 + " ms\n");
		sb.append("系统物理内存总计：" + osmb.getTotalPhysicalMemorySize() / 1024 / 1024 + " MB\n");
		sb.append("系统物理可用内存总计：" + osmb.getFreePhysicalMemorySize() / 1024 / 1024 + " MB\n");
		sb.append("系统交换内存总计：" + osmb.getTotalSwapSpaceSize() / 1024 / 1024 + " MB\n");
		sb.append("系统交换可用内存总计：" + osmb.getFreeSwapSpaceSize() / 1024 / 1024 + " MB");
		return sb.toString();
	}
	
	public static void getSystemInfo() {
		SystemInfoBean.diskInfo = getDiskInfo();
		OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		SystemInfoBean.sysName = osmb.getName();
		SystemInfoBean.sysVersion = osmb.getVersion();
		SystemInfoBean.sysArch = osmb.getArch();
		SystemInfoBean.sysCpuLoad = String.valueOf(osmb.getSystemCpuLoad());
		SystemInfoBean.sysLoadAvg = String.valueOf(osmb.getSystemLoadAverage());
		SystemInfoBean.sysProcCpuLoad = String.valueOf(osmb.getProcessCpuLoad());
		SystemInfoBean.sysTotalMemSize = String.valueOf(osmb.getTotalPhysicalMemorySize() / 1024 / 1024) + " MB";
		SystemInfoBean.sysFreeMemSize = String.valueOf(osmb.getFreePhysicalMemorySize() / 1024 / 1024) + " MB";
		SystemInfoBean.sysTotalSwapMemSize = String.valueOf(osmb.getTotalSwapSpaceSize() / 1024 / 1024) + " MB";
		SystemInfoBean.sysFreeSwapMemSize = String.valueOf(osmb.getFreeSwapSpaceSize() / 1024 / 1024) + " MB";
	}

	public static void main(String[] param) {
		System.out.println(getDiskInfo());
		System.out.println(getEMS());
	}
}
