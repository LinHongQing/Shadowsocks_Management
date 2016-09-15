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
import dao.DBOperator;
import utils.ClassReflectUtil;

/**
 * Servlet implementation class HandleDeleteMember
 */
@WebServlet("/admin/delete-user.action")
public class HandleDeleteMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberInfoBean member;
	private String msgType;
	private Object msgContent;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleDeleteMember() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			member = (MemberInfoBean) ClassReflectUtil.getObjectFromHttpRequestParameters(new MemberInfoBean(), request);
			String[] param = {"id"};
			String sql = ClassReflectUtil.getDeleteObjectSql(member, "user", param);
			DBOperator.initDBConnection();
			if (DBOperator.excuteUpdateSqlQuery(sql) == -1) {
				msgType = "error";
				msgContent = "删除失败";
			} else {
				msgType = "success";
				msgContent = "删除成功";
			}
			DBOperator.closeDBConnection();
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
