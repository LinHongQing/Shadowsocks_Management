package utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONUtil {
	
	private JSONObject json = null;
	
	public static String generateJSONString(String[] args) {
		try {
			if(args != null) {
				JSONObject json = new JSONObject();
				for(int i = 0; i < args.length; i += 2) {
					json.put(args[i], args[i + 1]);
				}
				return json.toString();
			}
			
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public String resolveJSONString(String param) {
		if(json != null) {
			try {
				return (String) json.get(param);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	public JSONUtil(String jsonStr) {
		super();
		// TODO Auto-generated constructor stub
		try {
			json = (JSONObject) new JSONTokener(jsonStr).nextValue();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
