package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//import utils.UrlUtil;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilter implements Filter {
	private String ADMIN_LOGIN_URL = null;
	private String USER_LOGIN_URL = null;

    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		if (ADMIN_LOGIN_URL == null)
			ADMIN_LOGIN_URL = httpRequest.getContextPath() + "/admin/";
		if (USER_LOGIN_URL == null)
			USER_LOGIN_URL = httpRequest.getContextPath() + "/user/";
		
		HttpSession session = httpRequest.getSession();
		if (isLoginUrl(httpRequest.getRequestURI())) {
			if (session.getAttribute("loginInfo") == null) {
//				session.setAttribute("forwardurl", UrlUtil.getURL(httpRequest));
				System.out.println(httpRequest.getRequestURI() + "未登录");
				httpRequest.getRequestDispatcher("../nologin.html").forward(request, response);
				return;
			} else {
				System.out.println(httpRequest.getRequestURI() + "已经登录");
				// pass the request along the filter chain
				chain.doFilter(request, response);
			}
		} else {
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
	}
	
	private boolean isLoginUrl(String strUrl) {
		if (strUrl.contains(ADMIN_LOGIN_URL)) {
			String login_html = strUrl.substring(strUrl.length() - "login".length(), strUrl.length());
			String login = strUrl.substring(strUrl.length() - "login.action".length(), strUrl.length());
			if ("login".equals(login_html) || "login.action".equals(login))
				return false;
			else
				return true;
		} else if (strUrl.contains(USER_LOGIN_URL))
			return true;
		else
			return false;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
