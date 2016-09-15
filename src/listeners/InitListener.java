package listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import services.GlobalService;

/**
 * Application Lifecycle Listener implementation class InitListener
 *
 */
@WebListener
public class InitListener implements ServletContextListener {

	/**
	 * Default constructor.
	 */
	public InitListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		GlobalService.stopGetRefreshSystemInformation();
		GlobalService.stopGetRefreshDbInformation();
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		String webAppRootKey = sce.getServletContext().getRealPath("/");
		System.setProperty("ssm.root", webAppRootKey);
		GlobalService.getConfigFromFile(System.getProperty("ssm.root"), "conf.json");
		GlobalService.startGetRefreshSystemInformation(5);
		GlobalService.startGetRefreshDbInformation(10*60);
		GlobalService.initDefaultRSAKeyPair();
	}

}
