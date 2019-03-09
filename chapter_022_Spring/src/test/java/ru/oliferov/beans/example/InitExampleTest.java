package ru.oliferov.beans.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


/**
 * @autor aoliferov
 * @since 07.03.2019
 */
public class InitExampleTest {

    @Test
    public void testXmlConfig() {
        //xml конфигурирование
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        // варианты создания бинов
        // 1) только по классу, если подходящий бин только 1
        UserStore userStore = context.getBean(UserStore.class);
        // 2) по id
        Store store1 = (Store) context.getBean("nameStore");
        // 3) по id и классу, если несколько подходящих по классу
        Store store2 = context.getBean("nameStore", Store.class);
        // бин по умолчанию @Scope = singleton
        assertSame(store1, store2);
        assertNotNull(store1);
    }

    @Test
    public void testJavaConfig() {
        // java-конфигурация
        // если классов отмеченных аннотацией @Configuration много, указывается директория с этими классами
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        UserStore storeU1 = context.getBean("userStore", UserStore.class);
        UserStore storeU2 = context.getBean("userStore", UserStore.class);
        //UserStore @Scope = prototype
        assertNotNull(storeU1);
        assertNotNull(storeU2);
        assertNotSame(storeU1, storeU2);
    }

    @Test
    public void testAutoConfig() {
        //автоматическое конфигурирование
        ApplicationContext context3 = new AnnotationConfigApplicationContext("ru.oliferov.beans");
        // варианты создания бинов
        Store store = context3.getBean("getStore", Store.class);
        assertNotNull(store);
    }

    @Test
    public void testSpringProp() {
        /* получение свойств */
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        String prop = context.getEnvironment().getProperty("db.username");
        assertEquals(prop, "root");
    }
}
