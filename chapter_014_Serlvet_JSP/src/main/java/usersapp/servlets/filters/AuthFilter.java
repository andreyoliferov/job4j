package usersapp.servlets.filters;


import usersapp.items.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @autor aoliferov
 * @since 05.12.2018
 */
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //
    }

    /**
     * Фильтр авторизации
     * Если пользователь неавторизован - редирект на страницу login
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String url = req.getRequestURI();
        if (!url.contains("soap")) {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("currentUser");
            if (user == null && !req.getRequestURI().contains("/login")) {
                ((HttpServletResponse) servletResponse).sendRedirect(String.format("%s/login", req.getContextPath()));
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //
    }
}
