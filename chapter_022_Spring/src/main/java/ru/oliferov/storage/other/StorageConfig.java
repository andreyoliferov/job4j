package ru.oliferov.storage.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import java.sql.SQLException;

/**
 * @autor aoliferov
 * @since 12.03.2019
 */
@Configuration
@PropertySource("classpath:users-storage.properties")
@ComponentScan("ru.oliferov.storage.other")
public class StorageConfig {

    @Autowired
    Environment env;

    @Bean(name = "jdbcStorage")
    @Scope("singleton")
    public JDBCStorage getJDBCStorage() throws SQLException, ClassNotFoundException {
        return new JDBCStorage(env);
    }

    @Bean(name = "jdbcApp")
    @Scope("singleton")
    public AppUser getAppJdbc(@Qualifier("jdbcStorage") Storage storage) {
        return new AppUser(storage);
    }

    @Bean(name = "hbApp")
    @Scope("singleton")
    public AppUser getAppHb(@Qualifier("hbStorage") Storage storage) {
        return new AppUser(storage);
    }

    @Bean(name = "memApp")
    @Scope("singleton")
    public AppUser getAppMem(@Qualifier("memStorage") Storage storage) {
        return new AppUser(storage);
    }
}
