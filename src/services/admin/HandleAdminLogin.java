package services.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.interfaces.RSAPrivateKey;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import bean.LoginInfoBean;
import bean.LoginPageBean;
import dao.DBOperator;
import utils.ClassReflectUtil;
import utils.DateTimeFormatUtil;
import utils.MD5Util;
import utils.RSAUtil;
import utils.RegexValidateUtil;

/**
 * Servlet implementation class HandleAdminLogin
 */
@WebServlet("/admin/login.action")
public class HandleAdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginPageBean login;
	private String session_verifyCode;
	private String msgType;
	private String msgContent;

	public final class AdminVerify {

		private int id;
		private int userid;

		public AdminVerify(int userid) {
			super();
			this.id = ClassReflectUtil._INVALID_NUM_;
			this.userid = userid;
		}

		/**
		 * @return the id
		 */
		public final int getId() {
			return id;
		}

		/**
		 * @param id
		 *            the id to set
		 */
		public final void setId(int id) {
			this.id = id;
		}

		/**
		 * @return the userid
		 */
		public final int getUserid() {
			return userid;
		}

		/**
		 * @param userid
		 *            the userid to set
		 */
		public final void setUserid(int userid) {
			this.userid = userid;
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HandleAdminLogin() {
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
		Map<String, Object> m = new HashMap<String, Object>();// 传递Map

		response.setContentType("text/javascript");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		loginValidate(request);

		m.put("type", msgType);
		m.put("content", msgContent);

		JSONObject json = new JSONObject(m);
		System.out.println(json.toString());
		out.print(json.toString());// 返给ajax请求
		out.flush();
		out.close();
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

	private void loginValidate(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			session_verifyCode = (String) session.getAttribute("verifycode");
			login = (LoginPageBean) ClassReflectUtil.getObjectFromHttpRequestParameters(new LoginPageBean(), request);
			System.out.println(login);
			
			if (login.getEmail() == null || login.getPass() == null || login.getVerifycode() == null) {
				msgType = "error";
				msgContent = "参数错误";
				return;
			}
			if (login.getEmail().isEmpty()) {
				msgType = "error";
				msgContent = "邮箱为空";
				return;
			}
			if (login.getPass().isEmpty()) {
				msgType = "error";
				msgContent = "密码为空";
				return;
			}
			if (login.getVerifycode().isEmpty()) {
				msgType = "error";
				msgContent = "验证码为空";
				return;
			}
			if (!RegexValidateUtil.checkEmail(login.getEmail())) {
				msgType = "error";
				msgContent = "邮箱地址不合法";
				return;
			}
			if (!session_verifyCode.equals(login.getVerifycode().toLowerCase())) {
				msgType = "error";
				msgContent = "验证码错误";
				return;
			}
			RSAPrivateKey privateKey = (RSAPrivateKey) request.getSession().getAttribute("privateKey");
			String passwd = RSAUtil.decryptStringByJs(login.getPass(), privateKey);
			System.out.println(passwd);

			login.setPass(MD5Util.MD5(passwd));
			login.setVerifycode(ClassReflectUtil._INVALID_STRING_);
			
			session.removeAttribute("privateKey");
			session.removeAttribute("verifycode");
			
			String sql = ClassReflectUtil.getSelectObjectSql(login, "user");

			DBOperator.initDBConnection();
			ResultSet rs = DBOperator.excuteQuerySql(sql);
			if (rs.next() != false) {
				if (rs.getString("is_enabled").equals("0")) {
					msgType = "error";
					msgContent = "该账户已停用, 无法登录";
				} else {
					int userid = rs.getInt("id");
					String username = rs.getString("username");
					String loginIpAddress = request.getRemoteAddr();
					AdminVerify verifyAdmin = new AdminVerify(userid);
					
					String sql_verifyAdmin = ClassReflectUtil.getSelectObjectSql(verifyAdmin, "admin");
					rs = DBOperator.excuteQuerySql(sql_verifyAdmin);
					if (rs.next() != false) {
						LoginInfoBean loginInfo = new LoginInfoBean();
						loginInfo.setUserid(userid);
						loginInfo.setUsername(username);
						loginInfo.setLoginipaddress(loginIpAddress);
						loginInfo.setLogintime(DateTimeFormatUtil.getCurrentUnixTimeStamp());
						loginInfo.setIsadmin(true);
						session.setAttribute("loginInfo", loginInfo);
						msgType = "success";
						msgContent = "验证通过";
					} else {
						msgType = "error";
						msgContent = "您不是管理员, 无法登录";
					}
				}
			} else {
				msgType = "error";
				msgContent = "邮箱或密码错误";
			}
		} catch (Exception e) {
			e.printStackTrace();
			msgType = "error";
			msgContent = "服务器内部错误";
		} finally {
			DBOperator.closeDBConnection();
		}
	}

}
