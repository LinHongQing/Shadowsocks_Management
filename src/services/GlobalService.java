package services;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import bean.ConfigParamBean;
import utils.FileOperateUtil;
import utils.JSONUtil;
import utils.RSAUtil;

public class GlobalService {
	
	private static RefreshSystemInfoThread t_sys = null;
	private static RefreshDbInfoThread t_db = null;

	public static void getConfigFromFile(String filePath, String fileName) {
		JSONUtil json = new JSONUtil(FileOperateUtil.readFile(filePath, fileName));
		ConfigParamBean.dbAddress = json.resolveJSONString("dbAddress");
		ConfigParamBean.dbPort = json.resolveJSONString("dbPort");
		ConfigParamBean.dbName = json.resolveJSONString("dbName");
		ConfigParamBean.dbUsername = json.resolveJSONString("dbUsername");
		ConfigParamBean.dbPassword = json.resolveJSONString("dbPassword");
		ConfigParamBean.userDefaultClientPassword = json.resolveJSONString("userDefaultClientPassword");
		ConfigParamBean.userDefaultLoginPassword = json.resolveJSONString("userDefaultLoginPassword");
	}
	
	public static boolean verifyConfigParam() {
		if (ConfigParamBean.dbPort == null ||
				"0".equals(ConfigParamBean.dbPort) ||
				ConfigParamBean.dbName == null ||
				ConfigParamBean.dbName.isEmpty() ||
				ConfigParamBean.dbUsername == null ||
				ConfigParamBean.dbUsername.isEmpty() ||
				ConfigParamBean.dbPassword == null ||
				ConfigParamBean.dbPassword.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public static boolean writeConfigToFile(String filePath, String fileName) {
		Map<String, String> config = new HashMap<>();
		config.put("dbAddress", ConfigParamBean.dbAddress);
		config.put("dbPort", ConfigParamBean.dbPort);
		config.put("dbName", ConfigParamBean.dbName);
		config.put("dbUsername", ConfigParamBean.dbUsername);
		config.put("dbPassword", ConfigParamBean.dbPassword);
		config.put("userDefaultClientPassword", ConfigParamBean.userDefaultClientPassword);
		config.put("userDefaultLoginPassword", ConfigParamBean.userDefaultLoginPassword);
		JSONObject config_json = new JSONObject(config);
		System.out.println(config_json.toString());
		if (FileOperateUtil.writeStringToFile(config_json.toString(), filePath, fileName))
			return true;
		else
			return false;
	}
	
	public static void initDefaultRSAKeyPair() {
		RSAUtil.generateKeyPair(true);
	}
	
	public static void startGetRefreshSystemInformation(int interval) {
		t_sys = new RefreshSystemInfoThread(10);
		try {
			t_sys.start();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void stopGetRefreshSystemInformation() {
		if (t_sys != null) {
			t_sys.interrupt();
		}
		t_sys = null;
	}
	
	public static void startGetRefreshDbInformation(int interval) {
		t_db = new RefreshDbInfoThread(interval);
		try {
			t_db.start();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void stopGetRefreshDbInformation() {
		if (t_db != null) {
			t_db.interrupt();
		}
		t_db = null;
	}

}
