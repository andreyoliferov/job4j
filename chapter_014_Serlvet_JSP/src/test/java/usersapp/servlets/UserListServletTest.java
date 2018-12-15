package usersapp.servlets;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import usersapp.Validate;
import usersapp.ValidateService;
import util.ValidateMock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @autor aoliferov
 * @since 15.12.2018
 */
@PrepareForTest({ValidateService.class})
public class UserListServletTest extends PowerMockTestCase {

    private HttpServletRequest req;
    private HttpServletResponse resp;
    private Validate validate;
    private RequestDispatcher rd;

    @BeforeMethod
    public void setUp() {
        validate = new ValidateMock();
        PowerMockito.mockStatic(ValidateService.class);
        when(ValidateService.getInstance()).thenReturn(validate);
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        rd = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher(anyString())).thenReturn(rd);
    }

    @Test
    public void whenRequestList() throws ServletException, IOException {
        new UserListServlet().doGet(req, resp);

        verify(req).setAttribute(eq("users"), eq(validate.findAll()));
        verify(req).getRequestDispatcher(eq("WEB-INF/pages/listPage.jsp"));
        verify(rd).forward(eq(req), eq(resp));
    }

}