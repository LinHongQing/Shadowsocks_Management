package services.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bean.MemberInfoBean;
import bean.ConfigParamBean;
import dao.DBOperator;
import pojo.User;
import utils.ClassReflectUtil;
import utils.DateTimeFormatUtil;
import utils.MD5Util;
import utils.RegexValidateUtil;

/**
 * Servlet implementation class HandleAddUser
 */
@WebServlet("/admin/add-user.action")
public class HandleAddMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberInfoBean member;
	private String msgType;
	private String msgContent;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HandleAddMember() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			member = (MemberInfoBean) ClassReflectUtil.getObjectFromHttpRequestParameters(new MemberInfoBean(),
					request);

			if (member.getEmail() == null
					|| member.getUsername() == null
					|| RegexValidateUtil.checkEmail(member.getEmail()) == false
					|| member.getPort() == ClassReflectUtil._INVALID_NUM_
					|| member.getPort() > 65535
					|| member.getPort() < 1024
					|| member.getTransfer_enable() < 0
					|| member.getIs_enabled() < 0
					|| member.getIs_enabled() > 1) {
				msgType = "error";
				msgContent = "参数错误";
				sendMsgtoWeb(response);
				System.out.println(member.toString());
				return;
			} else {
				User user = new User(
						member.getUsername(),
						member.getEmail(),
						MD5Util.MD5(ConfigParamBean.userDefaultLoginPassword),
						ConfigParamBean.userDefaultClientPassword,
						DateTimeFormatUtil.getCurrentUnixTimeStamp(),
						member.getTransfer_enable(),
						member.getPort(),
						member.getIs_enabled());
				DBOperator.initDBConnection();
				if (DBOperator.excuteUpdateSqlQuery(ClassReflectUtil.getInsertObjectSql(user, "user")) == -1) {
					msgType = "error";
					msgContent = "用户添加失败";
					sendMsgtoWeb(response);
				}
				msgType = "success";
				msgContent = "提交成功";
				sendMsgtoWeb(response);
				System.out.println(member.toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			msgType = "error";
			msgContent = "服务器内部错误";
			sendMsgtoWeb(response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
