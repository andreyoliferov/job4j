package ru.oliferov.storage.other;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.UUID;

import static org.testng.Assert.*;

/**
 * @autor aoliferov
 * @since 12.03.2019
 */
public class AppUserTest {

    private ApplicationContext context;

    @BeforeClass
    public void start() {
        context = new AnnotationConfigApplicationContext(StorageConfig.class);
    }

    @DataProvider
    public Object[][] data() {
        return new Object[][]{
                {"memApp"},
                {"hbApp"},
                {"jdbcApp"} //настроить Travis
        };
    }
    //@Test(dataProvider = "data")
    public void testWhenAddUserThenIdNotNull(String bean) {
        AppUser app = context.getBean(bean, AppUser.class);
        UUID id = app.add(new User("testName", "testLogin"));
        assertNotNull(id);
        app.delete(id);
    }
}