package usersapp.servlets;


import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import usersapp.*;
import util.Param;
import util.ValidateMock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * @autor aoliferov
 * @since 10.12.2018
 */
@PrepareForTest({ValidateService.class})
public class SignInControllerTest extends PowerMockTestCase {

    private HttpServletRequest req;
    private HttpServletResponse resp;
    private RequestDispatcher rd;
    private HttpSession session;
    private Validate validate;

    @BeforeMethod
    public void start() {
        validate = new ValidateMock();
        PowerMockito.mockStatic(ValidateService.class);
        when(ValidateService.getInstance()).thenReturn(validate);
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        rd = mock(RequestDispatcher.class);
        session = mock(HttpSession.class);
        when(req.getRequestDispatcher(anyString())).thenReturn(rd);
        when(req.getSession()).thenReturn(session);
    }


    @Test
    public void whenLoginCorrect() throws ServletException, IOException {
        when(req.getParameter("login")).thenReturn("administratorLogin");
        when(req.getParameter("password")).thenReturn("administratorPassword");
        when(req.getContextPath()).thenReturn("/edit");

        new SignInController().doPost(req, resp);

        verify(session).setAttribute(eq("currentUser"), eq(validate.findById(new Param("id", "544bbba5-25e4-4b81-9384-04734ac61d48").get())));
        verify(session).setAttribute(eq("dataAccessReload"), eq(true));
        verify(resp).sendRedirect(eq("/edit/"));
        verify(req, never()).setAttribute(eq("error"), any());
    }

    @Test
    public void whenLoginIncorrect() throws ServletException, IOException {
        when(req.getParameter("login")).thenReturn("user");
        when(req.getParameter("password")).thenReturn("qwerty");
        when(req.getContextPath()).thenReturn("/list");

        new SignInController().doPost(req, resp);

        verify(req).setAttribute(eq("error"), eq("incorrect data"));
        verify(session, never()).setAttribute(eq("dataAccessReload"), any());
        verify(resp, never()).sendRedirect(any());
        verify(req).getRequestDispatcher(eq("WEB-INF/pages/loginPage.jsp"));
        verify(rd).forward(eq(req), eq(resp));
    }




}