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

import bean.SystemInfoBean;

/**
 * Servlet implementation class HandleGetSystemInfo
 */
@WebServlet("/admin/get-sys-info.action")
public class HandleGetSystemInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String msgType;
	private Object msgContent;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleGetSystemInfo() {
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
			info.put("adminCount", SystemInfoBean.adminCount);
			info.put("userCount", SystemInfoBean.userCount);
			info.put("sysName", SystemInfoBean.sysName);
			info.put("sysVersion", SystemInfoBean.sysVersion);
			info.put("sysArch", SystemInfoBean.sysArch);
			info.put("sysCpuLoad", SystemInfoBean.sysCpuLoad);
			info.put("sysLoadAvg", SystemInfoBean.sysLoadAvg);
			info.put("sysProcCpuLoad", SystemInfoBean.sysProcCpuLoad);
			info.put("sysTotalMemSize", SystemInfoBean.sysTotalMemSize);
			info.put("sysFreeMemSize", SystemInfoBean.sysFreeMemSize);
			info.put("sysTotalSwapMemSize", SystemInfoBean.sysTotalSwapMemSize);
			info.put("sysFreeSwapMemSize", SystemInfoBean.sysFreeSwapMemSize);
			info.put("diskInfo", SystemInfoBean.diskInfo);
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
