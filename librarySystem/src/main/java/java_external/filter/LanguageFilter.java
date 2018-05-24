package java_external.filter;

import java_external.utils.ResourceBundleManager;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class LanguageFilter implements Filter {

    private static final String USER_LOCALE_ATTR = "userLocale";
    private static final String ENCODING = "UTF-8";
    private static final String LANG_PARAM = "lang";


    public LanguageFilter() {
    }

    public void destroy() {
    }


    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(ENCODING);
        response.setCharacterEncoding(ENCODING);
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        Locale locale = (Locale) session.getAttribute(USER_LOCALE_ATTR);
        String lang = request.getParameter(LANG_PARAM);

        if (Objects.isNull(locale)) {
            locale = req.getLocale();
        }

        if (StringUtils.isNoneBlank(lang)) {
            if (!lang.equals(locale.getLanguage())) {
                locale = new Locale(lang);
            }
        }

        ResourceBundleManager.setNewLocale(locale);
        session.setAttribute(USER_LOCALE_ATTR, locale);
        chain.doFilter(request, response);
    }


    public void init(FilterConfig fConfig) throws ServletException {
    }

}
