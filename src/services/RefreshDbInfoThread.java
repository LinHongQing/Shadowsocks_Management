package services;

import java.sql.ResultSet;

import bean.SystemInfoBean;
import dao.DBOperator;

public class RefreshDbInfoThread extends Thread {
	int interval;
	ResultSet rs;
	public RefreshDbInfoThread(int interval) {
		super();
		this.interval = interval;
	}
	public void run() {
		while(true) {
			try {
				DBOperator.initDBConnection();
				rs = DBOperator.excuteQuerySql("select count(*) as user_count from user");
				if (rs.next()) {
					SystemInfoBean.userCount = rs.getInt("user_count");
				} else {
					SystemInfoBean.userCount = 0;
				}
				rs = DBOperator.excuteQuerySql("select count(*) as admin_count from admin");
				if (rs.next()) {
					SystemInfoBean.adminCount = rs.getInt("admin_count");
				} else {
					SystemInfoBean.adminCount = 0;
				}
				System.out.println("数据库信息已刷新!");
				Thread.sleep(interval * 1000);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				DBOperator.closeDBConnection();
				break;
			}
		}
		System.out.println("数据库信息刷新线程已终止");
	}
}
