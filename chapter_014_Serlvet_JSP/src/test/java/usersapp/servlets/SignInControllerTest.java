package usersapp.servlets;


import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.Test;
import usersapp.*;
import usersapp.items.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


//import static org.testng.Assert.*;

/**
 * @autor aoliferov
 * @since 10.12.2018
 */
@PrepareForTest({ValidateService.class})
public class SignInControllerTest extends PowerMockTestCase {

    @Test
    public void whenAddUserThenStoreIt() throws ServletException, IOException {
        Validate validate = ValidateMock.getInstance();
        PowerMockito.mockStatic(ValidateService.class);
        when(ValidateService.getInstance()).thenReturn(validate);


        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("name")).thenReturn("Petr Arsentev");
        new UserCreateServlet().doPost(req, resp);

        List<User> list = validate.findAll();
 //       assertThat(store.findAll().iterator().next().getName(), is("Petr Arsentev"));
    }
}