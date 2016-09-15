package services.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dao.DBOperator;
import pojo.User;
import utils.ClassReflectUtil;
import utils.DateTimeFormatUtil;

/**
 * Servlet implementation class HandleGetAllUsers
 */
@WebServlet("/admin/get-all-user.action")
public class HandleGetAllMembers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String msgType;
	private Object msgContent;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleGetAllMembers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			DBOperator.initDBConnection();
			List<Object> obj_users = ClassReflectUtil.getObjectsFromDb(new User(), "user");
			DBOperator.closeDBConnection();
			if (obj_users == null || obj_users.isEmpty()) {
				msgType = "empty";
				msgContent = "无内容";
				sendMsgtoWeb(response);
				return;
			}
			JSONObject result = new JSONObject();
			ArrayList<Map<String, Object>> users = new ArrayList<Map<String, Object>>();
			for (Object obj_user : obj_users) {
				User user = (User) obj_user;
				Map<String, Object> tableUser = new HashMap<String, Object>();
				tableUser.put("id", user.getId());
				tableUser.put("email", user.getEmail());
				tableUser.put("username", user.getUsername());
				tableUser.put("passwd", user.getPasswd());
				tableUser.put("t_registry", DateTimeFormatUtil.UnixTimeStamp2Date(user.getT_registry(), "yyyy-MM-dd hh:mm:ss"));
				if (user.getT() == 0)
					tableUser.put("t", "暂无");
				tableUser.put("t", DateTimeFormatUtil.UnixTimeStamp2Date(user.getT(), "yyyy-MM-dd hh:mm:ss"));
				tableUser.put("u", user.getU());
				tableUser.put("d", user.getD());
				tableUser.put("transfer_enable", user.getTransfer_enable());
				tableUser.put("port", user.getPort());
				tableUser.put("is_switched_on", user.getIs_switched_on());
				tableUser.put("is_enabled", user.getIs_enabled());
				users.add(tableUser);
			}
			result.put("tableContent", users);
			result.put("totalCount", users.size());
			
			msgType = "success";
			msgContent = result;
			
			sendMsgtoWeb(response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			msgType = "error";
			msgContent = "服务器内部错误";
			DBOperator.closeDBConnection();
			sendMsgtoWeb(response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void sendMsgtoWeb(HttpServletResponse response) {
		response.setContentType("text/javascript");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;

		try {
			out = response.getWriter();
			Map<String, Object> m = new HashMap<String, Object>();// 传递Map
			m.put("type", msgType);
			m.put("content", msgContent);
			JSONObject json = new JSONObject(m);
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		}

	}

}
