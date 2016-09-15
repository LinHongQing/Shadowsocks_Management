package services.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.LoginInfoBean;
import dao.DBOperator;
import pojo.Login;
import utils.ClassReflectUtil;

/**
 * Servlet implementation class HandleAdminLogout
 */
@WebServlet("/admin/logout.action")
public class HandleAdminLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleAdminLogout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		LoginInfoBean loginInfo = (LoginInfoBean) request.getSession().getAttribute("loginInfo");
    	if (loginInfo != null) {
    		Login login = new Login();
    		login.setUserid(loginInfo.getUserid());
    		login.setLoginipaddress(loginInfo.getLoginipaddress());
    		login.setLogintime(loginInfo.getLogintime());
    		try {
    			String sql = ClassReflectUtil.getInsertObjectSql(login, "login");
				DBOperator.initDBConnection();
				DBOperator.excuteUpdateSqlQuery(sql);
				DBOperator.closeDBConnection();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				DBOperator.closeDBConnection();
			}
    	}
		request.getSession().removeAttribute("loginInfo");
		response.sendRedirect(request.getContextPath() + "/admin/login");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
