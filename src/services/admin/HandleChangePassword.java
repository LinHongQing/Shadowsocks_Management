package services.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bean.MemberInfoBean;
import dao.DBOperator;
import utils.ClassReflectUtil;
import utils.MD5Util;
import utils.RSAUtil;

/**
 * Servlet implementation class HandleChangePassword
 */
@WebServlet("/admin/change-password.action")
public class HandleChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberInfoBean member;
	private String msgType;
	private Object msgContent;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleChangePassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String prepare = request.getParameter("prepare");
			String get = request.getParameter("get");
			String modify = request.getParameter("modify");
			if (prepare != null && get == null && modify == null) {
				String id = request.getParameter("id");
				if (id == null || "".equals(id)) {
					msgType = "error";
					msgContent = "参数错误";
					sendMsgtoWeb(response);
					return;
				}
				request.getSession().setAttribute("modifyid", id);
				msgType = "success";
				msgContent = "成功";
				sendMsgtoWeb(response);
				return;
			}
			if (get != null && prepare == null && modify == null) {
				DBOperator.initDBConnection();
				member = (MemberInfoBean)ClassReflectUtil.getObjectFromDb(new MemberInfoBean(), "user", Integer.parseInt((String) request.getSession().getAttribute("modifyid")));
				Map<String, Object> m = new HashMap<>();
				m.put("id", member.getId());
				m.put("username", member.getUsername());
				msgType = "success";
				msgContent = new JSONObject(m);
				sendMsgtoWeb(response);
				DBOperator.closeDBConnection();
				return;
			}
			if (modify != null && get == null && prepare == null) {
				member = (MemberInfoBean)ClassReflectUtil.getObjectFromHttpRequestParameters(new MemberInfoBean(), request);
				RSAPrivateKey privateKey = (RSAPrivateKey) request.getSession().getAttribute("privateKey");
				String passwd = RSAUtil.decryptStringByJs(member.getPass(), privateKey);
				member.setPass(MD5Util.MD5(passwd));
				request.getSession().removeAttribute("privateKey");
				
				String[] param = {"id"};
				String sql = ClassReflectUtil.getUpdateObjectSql(member, "user", param);
				DBOperator.initDBConnection();
				if (DBOperator.excuteUpdateSqlQuery(sql) == -1) {
					msgType = "error";
					msgContent = "修改失败";
					sendMsgtoWeb(response);
				} else {
					msgType = "success";
					msgContent = "修改成功";
					sendMsgtoWeb(response);
				}
				DBOperator.closeDBConnection();
			}
			msgType = "error";
			msgContent = "参数错误";
			sendMsgtoWeb(response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			msgType = "error";
			msgContent = "服务器内部错误";
			sendMsgtoWeb(response);
			DBOperator.closeDBConnection();
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
