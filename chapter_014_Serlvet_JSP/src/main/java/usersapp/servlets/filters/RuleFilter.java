package usersapp.servlets.filters;

import usersapp.DBStore;
import usersapp.items.Rule;
import usersapp.items.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @autor aoliferov
 * @since 05.12.2018
 */
public class RuleFilter implements Filter {

    private final DispatchAccess dispatchAccess = new DispatchAccess();
    private final LimitedAccess limitedAccess = new LimitedAccess();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Фильтр прав доступа
     * права доступа и данные доступа обновляются при авторизации по атрибуту dataAccessReload
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String path = req.getServletPath();
        HttpSession session = req.getSession();
        User user;
        synchronized (session) {
            user = (User) session.getAttribute("currentUser");
            Boolean reload = (Boolean) session.getAttribute("dataAccessReload");
            if (reload != null && reload) {
                dispatchAccess.reload();
                session.setAttribute("dataAccessReload", false);
            }
        }
        List<Rule> lacks = dispatchAccess.checkAccess(path, user);
        if (lacks.size() == 0 || limitedAccess.checkLimited(req)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse)
                    .sendError(403, String.format("User has no rules: %s", lacks.toString()));
        }
    }

    @Override
    public void destroy() {

    }

    private class DispatchAccess {

        private HashMap<String, Function<User, List<Rule>>> dispatch;
        private HashMap<String, List<Rule>> accessData;

        private void reload() {
            dispatch = new HashMap<>();
            accessData = DBStore.getInstance().accessData();
            accessData.forEach((path, expect) -> {
                dispatch.put(path, (user) -> {
                    List<Rule> rules = user.getRole().getRules();
                    List<Rule> errors = new ArrayList<>();
                    expect.forEach((ruleExpect) -> {
                        if (!rules.contains(ruleExpect)) {
                            errors.add(ruleExpect);
                        }
                    });
                    return errors;
                });
            });
        }

        private DispatchAccess() {
            reload();
        }

        private List<Rule> checkAccess(String path, User user) {
            List<Rule> result = new ArrayList<>();
            if (dispatch.keySet().contains(path)) {
                result = dispatch.get(path).apply(user);
            }
            return result;
        }
    }

    private class LimitedAccess {
        private HashMap<String, Predicate<HttpServletRequest>> access;

        private LimitedAccess() {
            access = new HashMap<>();
            access.put(
                    "/edit",
                    (req) -> {
                        HttpSession session = req.getSession();
                        User user;
                        synchronized (session) {
                            user = (User) session.getAttribute("currentUser");
                        }
                        boolean limited = user.getId().toString().equals(req.getParameter("id"));
                        if (limited) {
                            req.setAttribute("self", true);
                        }
                        return limited;
                    }
                    );
        }

        private boolean checkLimited(HttpServletRequest req) {
            boolean result = false;
            String path = req.getServletPath();
            if (access.keySet().contains(path)) {
                result = access.get(path).test(req);
            }
            return result;
        }
    }
}
