package org.oliferov.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.oliferov.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @autor aoliferov
 * @since 27.04.2019
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean //через этот мок можно подменять обращение к сервису, тем самым делая тесты модульными а не интеграционными
    private UserService service;

    @MockBean //тк в конфигурации Security используется доступ к БД, без бина DataSource выбрасывается исключение
    private DataSource dataSource;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenThen() throws Exception {
        this.mvc.perform(
                get("/persons").accept(MediaType.TEXT_HTML)
        ).andExpect(
                status().isOk()
        );
    }
}
