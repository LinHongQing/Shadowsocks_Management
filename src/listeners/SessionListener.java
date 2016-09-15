package listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import bean.LoginInfoBean;
import dao.DBOperator;
import pojo.Login;
import utils.ClassReflectUtil;

/**
 * Application Lifecycle Listener implementation class Session
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public SessionListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
    	LoginInfoBean loginInfo = (LoginInfoBean) arg0.getSession().getAttribute("loginInfo");
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
    }
	
}
