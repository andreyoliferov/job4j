package ru.oliferov.beans.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;

/**
 * @autor aoliferov
 * @since 09.03.2019
 */
public class AutowiredClass {

    @Autowired //к полям класса
    @Qualifier("main")
    //@Autowired(required = false) //чтобы не бросалось исключение,
    private Store greetingService;

    @Autowired //к полям класса в виде массива или коллекции
    private Store[] services;

    @Autowired //к Map, где ключами являются имена бинов, значения - сами бины
    private Map<String, Store> serviceMap;

    @Autowired //к конструктору, тем самым мы говорим для эта зависимость обязательная для создания бина, и на момент создания уже должна быть инициализирована
    public AutowiredClass(@Qualifier("main") Store service) {

    }

    @Autowired //к обычным методам с произвольным названием аргументов и их количеством
    public void prepare(Store prepareContext) {
        /* что-то делаем... */
    }

    @Autowired //к "традиционному" setter-методу
    public void setContext(Store service) {
        this.greetingService = service;
    }

}
