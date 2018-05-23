package java_external.filter;

import java_external.commands.manager.AccessMapper;
import java_external.db.dto.User;
import java_external.enums.Role;
import java_external.services.manager.ConfigurationManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class RoleFilter implements Filter {

    private static final String USER_ATTR = "user";
    private static final String ROLE_PARAM = "role";

    public RoleFilter() {
    }

    public void destroy() {
    }


    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER_ATTR);
        Role role;

        if (Objects.isNull(user)) {
            role = Role.GUEST;
        } else {
            role = user.getRole();
        }
        AccessMapper mapper = AccessMapper.getInstance();
        boolean enoughRights = mapper.checkRights(request, role);

        if (!enoughRights) {
            String page = ConfigurationManager.getInstance().getProperty(
                    ConfigurationManager.INDEX_PAGE_PATH);
//            String page = ConfigurationManager.getInstance().getProperty(
//                    ConfigurationManager.ERROR_PAGE_PATH);
            // ADD DEFAULT PAGE WITH MSG
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
            try {
                dispatcher.forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        chain.doFilter(request, response);
    }


    public void init(FilterConfig fConfig) throws ServletException {
    }

}
