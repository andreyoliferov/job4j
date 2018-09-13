package store;

import generic.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Задание "Статистика по коллекции.[#63984]"
 * @autor Андрей
 * @since 13.09.2018
 */
public class Store {

    /**
     * Объект статистики
     */
    private Info info;

    {
        info = new Info();
    }

    /**
     * Сложность алгоритма = O(n)
     * @param previoues начальные данные
     * @param current измененные данные
     * @return объект статистики
     */
    public Info diff(List<User> previoues, List<User> current) {
        HashMap<String, User> temp = new HashMap<>();
        for (User user : previoues) {
            temp.put(user.getId(), user);
        }
        for (User user : current) {
            User find = temp.get(user.getId());
            if (find == null) {
                info.addAdded(1);
            } else if (!find.equals(user)) {
                info.addChanged(1);
            }
        }
        info.addDeleted(previoues.size() - current.size() + info.getAdded());
        return info;
    }

    /**
     * варант с сортировкой, не лучшее решение
     * сложность n * log(n) по сортировке слиянием
     * @param previoues начальные данные
     * @param current измененные данные
     * @return объект статистики
     */
    public Info diff2(List<User> previoues, List<User> current) {
        Collections.sort(previoues);
        Collections.sort(current);
        int p = previoues.size();
        int c = current.size();
        int iP = 0;
        int iC = 0;
        while (iP < p && iC < c) {
            User pUser = previoues.get(iP);
            User cUser = current.get(iC);
            if (pUser.equals(cUser)) {
                iC++;
                iP++;
                continue;
            }
            if (pUser.getId().equals(cUser.getId())) {
                info.addChanged(1);
                iC++;
                iP++;
                continue;
            }
            if (pUser.compareTo(cUser) < 0) {
                info.addDeleted(1);
                iP++;
            } else {
                info.addAdded(1);
                iC++;
            }
        }
        info.addAdded(c - iC);
        info.addDeleted(p - iP);
        return info;
    }
}
