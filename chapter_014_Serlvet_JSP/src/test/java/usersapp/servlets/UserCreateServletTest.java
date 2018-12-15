package usersapp.servlets;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import usersapp.Validate;
import usersapp.ValidateService;
import usersapp.items.User;
import util.Param;
import util.ValidateMock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @autor aoliferov
 * @since 15.12.2018
 */
@PrepareForTest({ValidateService.class})
public class UserCreateServletTest extends PowerMockTestCase {

    private HttpServletRequest req;
    private HttpServletResponse resp;
    private RequestDispatcher rd;
    private Validate validate;

    @BeforeMethod
    public void setUp() {
        validate = new ValidateMock();
        PowerMockito.mockStatic(ValidateService.class);
        when(ValidateService.getInstance()).thenReturn(validate);
        resp = mock(HttpServletResponse.class);
        req = mock(HttpServletRequest.class);
        rd = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher(anyString())).thenReturn(rd);
    }

    @Test
    public void whenCorrectCreate() throws ServletException, IOException {
        when(req.getParameterMap())
                .thenReturn(
                        new Param("name", "testUser")
                                .add("login", "testLogin")
                                .add("password", "testPassword")
                                .add("email", "testEmail")
                                .add("role", "7b642c55-7b3f-4e8c-964a-e8b5a5286ec2")
                        .get());

        new UserCreateServlet().doPost(req, resp);

        User user = validate.auth("testLogin", "testPassword");
        assertThat(user.getEmail(), is("testEmail"));
        assertThat(user.getName(), is("testUser"));
        assertThat(user.getRole().getId().toString(), is("7b642c55-7b3f-4e8c-964a-e8b5a5286ec2"));
        verify(req).setAttribute(eq("result"), eq(String.format("user created with id: %s", user.getId().toString())));
        verify(req).getRequestDispatcher(eq("WEB-INF/pages/createPage.jsp"));
        verify(rd).forward(eq(req), eq(resp));
    }

    @Test
    public void whenIncorrectCreate() throws ServletException, IOException {
        when(req.getParameterMap())
                .thenReturn(
                        new Param("name", "testUser")
                                .add("login", "testLogin")
                                .add("password", "testPassword")
                                .add("email", "testEmail")
                                .add("role", "7b642c55-7b3f-4e8c-964a-e8b5a5286ec2")
                                .add("exception", "test exception")
                                .get());

        new UserCreateServlet().doGet(req, resp);

        User user = validate.auth("testLogin", "testPassword");
        assert user == null;
        verify(req).setAttribute(eq("result"), eq("error: test exception"));
        verify(req).getRequestDispatcher(eq("WEB-INF/pages/createPage.jsp"));
        verify(rd).forward(eq(req), eq(resp));
    }

}