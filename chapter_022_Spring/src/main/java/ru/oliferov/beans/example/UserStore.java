package ru.oliferov.beans.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @autor aoliferov
 * @since 07.03.2019
 */
@Component
@Scope("prototype")
public class UserStore {

    //зависимость используется в MyConfig
    // @Autowired
    // @Qualifier("nameStore")
    private Store store;

    @Autowired
    public UserStore(@Qualifier("nameStore") Store store) {
        this.store = store;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    private void initMethod() {
    }

    private void destroyMethod() {
    }

}
