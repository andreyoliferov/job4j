package store;

import generic.User;

import java.util.Collections;
import java.util.List;

/**
 * @autor Андрей
 * @since 31.07.2018
 */
public class Store {

    private Info info;

    {
        info = new Info();
    }

    public Info diff(List<User> previoues, List<User> current) {
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
