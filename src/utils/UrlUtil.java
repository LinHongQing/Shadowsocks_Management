package utils;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class UrlUtil {
	public static String getURL(HttpServletRequest request) {
		String url = "";
		url = request.getRequestURI() + "?";
		url += Param(request);
		return url;
	}
	public static String Param(HttpServletRequest request) {
		String url = "";
		Enumeration<String> param = request.getParameterNames();

		while (param.hasMoreElements()) {
			String pname = param.nextElement().toString();
			url += pname + "=" + request.getParameter(pname) + "&";
		}

		if (url.endsWith("&")) {
			url = url.substring(0, url.lastIndexOf("&"));
		}
		return url;
	}
}
