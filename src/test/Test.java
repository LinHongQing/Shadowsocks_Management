package test;

import services.GlobalService;

public class Test {
	public static void main(String[] param) {
		GlobalService.startGetRefreshSystemInformation(1);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GlobalService.stopGetRefreshSystemInformation();
	}
}
