package ru.job4j.user;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @autor Андрей
 * @since 30.06.2018
 */
public class UserConvert {

    /**
     * метод конвертирует list пользователей в hashmap
     * @param list лист пользователей
     * @return map ключ id/ value user
     */
    public HashMap<UUID, User> process(List<User> list) {
        HashMap<UUID, User> result = new HashMap<>();
        list.forEach(user -> result.put(user.getID(), user));
        return result;
    }
}
