package services;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.VerifyCodeUtil;

/**
 * Servlet implementation class GetVerifyCode
 */
@WebServlet("/get_verify_code.action")
public class GetVerifyCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetVerifyCode() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		
		//生成随机字串
		String verifyCode = VerifyCodeUtil.generateVerifyCode(4);
		//存入会话session
		HttpSession session = request.getSession(true);
		session.setAttribute("verifycode", verifyCode.toLowerCase());
		//生成图片
		int w = 130, h = 50;
		VerifyCodeUtil.outputImage(w, h, response.getOutputStream(), verifyCode);
		System.out.println("获取的验证码:" + verifyCode);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
