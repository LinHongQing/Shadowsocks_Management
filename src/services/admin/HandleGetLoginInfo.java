package services.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bean.LoginInfoBean;
import dao.DBOperator;
import utils.DateTimeFormatUtil;

/**
 * Servlet implementation class HandleGetLoginInfo
 */
@WebServlet("/admin/get-login-info.action")
public class HandleGetLoginInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String msgType;
	private Object msgContent;
    ResultSet rs;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleGetLoginInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			Map<String, Object> info = new HashMap<>();
			LoginInfoBean loginInfo = (LoginInfoBean) request.getSession().getAttribute("loginInfo");
			int userid = loginInfo.getUserid();
			String username = loginInfo.getUsername();
			String loginIpAddress = loginInfo.getLoginipaddress();
			String loginCount = null;
			String lasttimeLoginIpAddress = null;
			String lasttimeLoginTime = null;
			DBOperator.initDBConnection();
			rs = DBOperator.excuteQuerySql("select * from login where userid = " + userid + " order by logintime desc limit 1");
			if (!rs.next()) {
				lasttimeLoginIpAddress = "暂无";
				lasttimeLoginTime = "暂无";
			} else {
				lasttimeLoginIpAddress = rs.getString("loginipaddress");
				lasttimeLoginTime = DateTimeFormatUtil.UnixTimeStamp2Date(rs.getInt("logintime"), "yyyy-MM-dd hh:mm:ss");
			}
			rs = DBOperator.excuteQuerySql("select count(*) as logincount from login where userid = " + userid);
			if (!rs.next()) {
				loginCount = "0";
			} else {
				loginCount = String.valueOf(rs.getInt("logincount") + 1);
			}
			info.put("username", username);
			info.put("loginIpAddress", loginIpAddress);
			info.put("loginCount", loginCount);
			info.put("lasttimeLoginIpAddress", lasttimeLoginIpAddress);
			info.put("lasttimeLoginTime", lasttimeLoginTime);
			msgType = "success";
			msgContent = info;
			sendMsgtoWeb(response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			msgType = "error";
			msgContent = "服务器内部错误";
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
