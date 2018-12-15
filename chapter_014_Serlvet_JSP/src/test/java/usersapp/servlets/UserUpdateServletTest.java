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
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @autor aoliferov
 * @since 15.12.2018
 */
@PrepareForTest({ValidateService.class})
public class UserUpdateServletTest extends PowerMockTestCase {

    private HttpServletRequest req;
    private HttpServletResponse resp;
    private RequestDispatcher rd;
    private Validate validate;

    @BeforeMethod
    public void setUp() {
        validate = new ValidateMock();
        PowerMockito.mockStatic(ValidateService.class);
        when(ValidateService.getInstance()).thenReturn(validate);
        req = mock(HttpServletRequest.class);
        rd = mock(RequestDispatcher.class);
        resp = mock(HttpServletResponse.class);
        when(req.getRequestDispatcher(anyString())).thenReturn(rd);
    }

    @Test
    public void whenCorrectUpdate() throws ServletException, IOException {
        when(req.getParameterMap())
                .thenReturn(
                        new Param("id", "544bbba5-25e4-4b81-9384-04734ac61d48")
                                .add("name", "testUser")
                                .add("login", "testLogin")
                                .add("password", "testPassword")
                                .add("email", "testEmail")
                                .add("role", "7b642c55-7b3f-4e8c-964a-e8b5a5286ec2")
                                .get());

        new UserUpdateServlet().doPost(req, resp);

        User user = validate.auth("administratorLogin", "administratorPassword");
        assert user == null;
        user = validate.auth("testLogin", "testPassword");
        assertThat(user.getId(), is(UUID.fromString("544bbba5-25e4-4b81-9384-04734ac61d48")));
        assertThat(user.getEmail(), is("testEmail"));
        assertThat(user.getName(), is("testUser"));
        assertThat(user.getRole().getId().toString(), is("7b642c55-7b3f-4e8c-964a-e8b5a5286ec2"));
        verify(req).setAttribute(eq("result"), eq("User successfully updated!"));
        verify(req).getRequestDispatcher(eq("WEB-INF/pages/updatePage.jsp"));
        verify(rd).forward(eq(req), eq(resp));
    }

    @Test
    public void whenIncorrectUpdate() throws ServletException, IOException {
        when(req.getParameterMap())
                .thenReturn(
                        new Param("id", "544bbba5-25e4-4b81-9384-04734ac61d48")
                                .add("name", "testUser")
                                .add("login", "testLogin")
                                .add("password", "testPassword")
                                .add("email", "testEmail")
                                .add("role", "7b642c55-7b3f-4e8c-964a-e8b5a5286ec2")
                                .add("exception", "test exception")
                                .get());

        new UserUpdateServlet().doPost(req, resp);

        User user = validate.auth("administratorLogin", "administratorPassword");
        assertThat(user.getId(), is(UUID.fromString("544bbba5-25e4-4b81-9384-04734ac61d48")));
        assertThat(user.getEmail(), is("administrator@mail.ru"));
        assertThat(user.getName(), is("administratorName"));
        assertThat(user.getRole().getId().toString(), is("09888872-58a9-40aa-935f-b0a56dba7fff"));
        user = validate.auth("testLogin", "testPassword");
        assert user == null;
        verify(req).setAttribute(eq("user"), eq(null));
        verify(req).setAttribute(eq("result"), eq("error: test exception"));
        verify(req).getRequestDispatcher(eq("WEB-INF/pages/updatePage.jsp"));
        verify(rd).forward(eq(req), eq(resp));
    }

    @Test
    public void whenUserIsNotExist() throws ServletException, IOException {
        when(req.getParameterMap())
                .thenReturn(
                        new Param("id", "544bbba5-25e4-4b81-9384-04734ac61d56")
                                .add("name", "testUser")
                                .add("login", "testLogin")
                                .add("password", "testPassword")
                                .add("email", "testEmail")
                                .add("role", "7b642c55-7b3f-4e8c-964a-e8b5a5286ec2")
                                .get());

        new UserUpdateServlet().doPost(req, resp);

        User user = validate.auth("testLogin", "testPassword");
        assert user == null;
        verify(req).setAttribute(eq("user"), eq(null));
        verify(req).setAttribute(eq("result"), eq("error: User does not exist"));
        verify(req).getRequestDispatcher(eq("WEB-INF/pages/updatePage.jsp"));
        verify(rd).forward(eq(req), eq(resp));
    }

    @Test
    public void whenUserIsNotExistGet() throws ServletException, IOException {
        when(req.getParameterMap())
                .thenReturn(
                        new Param("id", "544bbba5-25e4-4b81-9384-04734ac61d56").get());

        new UserUpdateServlet().doGet(req, resp);

        User user = validate.auth("testLogin", "testPassword");
        assert user == null;
        verify(req).setAttribute(eq("user"), eq(null));
        verify(req).setAttribute(eq("result"), eq("error: User does not exist"));
        verify(req).getRequestDispatcher(eq("WEB-INF/pages/updatePage.jsp"));
        verify(rd).forward(eq(req), eq(resp));
    }
}