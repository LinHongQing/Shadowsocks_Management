package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.ConfigParamBean;
import services.GlobalService;
import utils.ClassReflectUtil;

public class DBOperator {

	private static Connection conn = null;
	private static PreparedStatement psmt = null;
	private static Statement stmt = null;
	private static ResultSet rs = null;

	public static Connection initDBConnection() {
		try {
			if (conn != null)
				return conn;
			Class.forName("com.mysql.jdbc.Driver");
			if (GlobalService.verifyConfigParam()) {
				String url = "jdbc:mysql://" + ConfigParamBean.dbAddress + ':' + ConfigParamBean.dbPort + '/'
						+ ConfigParamBean.dbName;
				conn = DriverManager.getConnection(url, ConfigParamBean.dbUsername, ConfigParamBean.dbPassword);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return conn;
	}

	public static void closeDBConnection() {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
			if (psmt != null) {
				psmt.close();
				psmt = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static boolean saveObject(Object object, String tableName) {
		if (conn == null)
			conn = initDBConnection();
		String sql = ClassReflectUtil.getInsertObjectSql(object, tableName);
		try {
			psmt = conn.prepareStatement(sql);
			psmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static ResultSet loadObject(String tableName, int id) {
		if (conn == null)
			conn = initDBConnection();
		String sql;
		if (id == -1)
			sql = "select * from " + tableName;
		else
			sql = "select * from " + tableName + " where id = " + id;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static ResultSet loadObjects(String tableName) {
		return loadObject(tableName, -1);
	}
	
	public static ResultSet excuteQuerySql(String sql) {
		if (conn == null)
			conn = initDBConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static int excuteUpdateSqlQuery(String sql) {
		if (conn == null)
			conn = initDBConnection();
		try {
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(sql);
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
}
