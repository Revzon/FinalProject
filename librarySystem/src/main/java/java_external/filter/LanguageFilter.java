package java_external.filter;

import java_external.utils.ResourceBundleManager;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LanguageFilter implements Filter {

	private static final String USER_LOCALE_ATTR = "userLocale";
	private static final String ENCODING = "UTF-8";
	private static final String LANG_PARAM = "lang";


	public LanguageFilter() {}

	public void destroy() {	}


	public void doFilter(ServletRequest request, ServletResponse response,
						 FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(ENCODING);
//
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpSession session = req.getSession();
//		Locale locale = null;
//		String lang = request.getParameter(LANG_PARAM);
//
//		if(session.getAttribute(USER_LOCALE_ATTR) == null){
//			locale = req.getLocale();
//		} else {
//			if (lang != null) {
//				locale = new Locale(lang);
//			} else {
//                locale = new Locale("en_GB");
//            }
//		}
//		ResourceBundleManager.setNewLocale(locale);
//		session.setAttribute(USER_LOCALE_ATTR, locale);
		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {}

}
