package services;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import bean.ValidateParamBean;
import dao.DBOperator;
import utils.ClassReflectUtil;

/**
 * Servlet implementation class HandleValidateParam
 */
@WebServlet("/validate.action")
public class HandleValidateParam extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ValidateParamBean validateParam; 
	private String msgType;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleValidateParam() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			validateParam = (ValidateParamBean) ClassReflectUtil.getObjectFromHttpRequestParameters(new ValidateParamBean(), request);
			DBOperator.initDBConnection();
			int id = validateParam.getId();
			validateParam.setId(ClassReflectUtil._INVALID_NUM_);
			ResultSet rs = DBOperator.excuteQuerySql(ClassReflectUtil.getSelectObjectSql(validateParam, "user"));
			PrintWriter out = response.getWriter();
			if (rs.next() != false) {
				if (id == rs.getInt("id"))
					msgType = "true";
				else
					msgType = "false";
				DBOperator.closeDBConnection();
				out.print(msgType);
				out.flush();
				out.close();
				return;
			}
			msgType = "true";
			rs.close();
			DBOperator.closeDBConnection();
			out.print(msgType);
			out.flush();
			out.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
