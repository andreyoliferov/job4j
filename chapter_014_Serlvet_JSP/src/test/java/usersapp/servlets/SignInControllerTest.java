package usersapp.servlets;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import org.testng.annotations.Test;
import usersapp.DBStore;
import usersapp.MemoryStore;
import usersapp.Store;
import usersapp.ValidateService;
import usersapp.items.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


//import static org.testng.Assert.*;

/**
 * @autor aoliferov
 * @since 10.12.2018
 */
//@RunWith(PowerMockRunner.class)
@PrepareForTest(DBStore.class)
public class SignInControllerTest {

    @Test
    public void whenAddUserThenStoreIt1() throws ServletException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("name")).thenReturn("Petr Arsentev");
        new UserServlet().doPost(req, resp);
    }


    @Test
    public void whenAddUserThenStoreIt() throws ServletException, IOException {
        Store store = MemoryStore.getInstance();
        PowerMockito.mockStatic(DBStore.class);
        when((Store) DBStore.getInstance()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("name")).thenReturn("Petr Arsentev");
        new UserCreateServlet().doPost(req, resp);

        List<User> list = store.findAll();

 //       assertThat(store.findAll().iterator().next().getName(), is("Petr Arsentev"));
    }

//    @Test
//    public void whenAddUserThenStoreIt() throws ServletException, IOException {
//        Validate validate = new ValidateStub();
//        PowerMockito.mockStatic(ValidateService.class);
//        Mockito.when(ValidateService.getInstance()).thenReturn(validate);
//        HttpServletRequest req = mock(HttpServletRequest.class);
//        HttpServletResponse resp = mock(HttpServletResponse.class);
//        when(req.getParameter("name")).thenReturn("Petr Arsentev");
//        new UserServlet().doPost(req, resp);
//        assertThat(validate.getAll().iterator().next().getName(), is("Petr Arsentev"));
//    }

}