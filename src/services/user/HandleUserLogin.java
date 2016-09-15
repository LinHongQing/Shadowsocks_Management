package services.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import bean.LoginPageBean;
import utils.RegexValidateUtil;

/**
 * Servlet implementation class HandleLogin
 */
@WebServlet("/login.action")
public class HandleUserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginPageBean userInfo;
	private String session_verifyCode;
	private String msgType;
	private String msgContent;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HandleUserLogin() {
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
		HttpSession session = request.getSession();
		Map<String, Object> m = new HashMap<String, Object>();// 传递Map
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		session_verifyCode = (String) session.getAttribute("verifycode");
		userInfo = new LoginPageBean();
		userInfo.setEmail(request.getParameter("email"));
		userInfo.setPass(request.getParameter("password"));
		userInfo.setVerifycode(request.getParameter("verifycode"));
		System.out.println(userInfo);
		
		loginValidate();
		
		m.put("type", msgType);
		m.put("content", msgContent);

		JSONObject json = new JSONObject(m);
		System.out.println(json.toString());
		out.print(json.toString());// 返给ajax请求
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
	
	private void loginValidate() {
		if (userInfo.getEmail() == null || userInfo.getPass() == null || userInfo.getVerifycode() == null) {
			msgType = "error";
			msgContent = "参数错误";
			return;
		}
		if(userInfo.getEmail().isEmpty()) {
			msgType = "error";
			msgContent = "邮箱为空";
			return;
		}
		if(userInfo.getPass().isEmpty()) {
			msgType = "error";
			msgContent = "密码为空";
			return;
		}
		if(userInfo.getVerifycode().isEmpty()) {
			msgType = "error";
			msgContent = "验证码为空";
			return;
		}
		if (!RegexValidateUtil.checkEmail(userInfo.getEmail())) {
			msgType = "error";
			msgContent = "邮箱地址不合法";
			return;
		}
		if (!session_verifyCode.equals(userInfo.getVerifycode())) {
			msgType = "error";
			msgContent = "验证码错误";
			return;
		}
		
		msgType = "success";
		msgContent = "验证通过";
	}

}
