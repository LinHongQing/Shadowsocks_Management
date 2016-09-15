package services;

import utils.SystemInfoUtil;

public class RefreshSystemInfoThread extends Thread {
	int interval;
	public RefreshSystemInfoThread(int interval) {
		super();
		this.interval = interval;
	}
	public void run() {
		while(true) {
			try {
				SystemInfoUtil.getSystemInfo();
				System.out.println("系统信息已刷新!");
				Thread.sleep(interval * 1000);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				break;
			}
		}
		System.out.println("系统信息刷新线程已终止");
	}
}
