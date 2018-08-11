package synchro;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @autor Андрей
 * @since 11.08.2018
 */
@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private List<User> data;

    {
        data = new ArrayList<>();
    }

    public synchronized boolean add(User user) {
        return data.add(user);
    }

    public synchronized boolean update(User user) {
        boolean result = false;
        int index = findIndexById(user.getId());
        if (index != -1) {
            data.set(index, user);
            result = true;
        }
        return result;
    }

    public synchronized boolean delete(User user) {
        return data.remove(user);
    }

    public synchronized boolean transfer(UUID fromId, UUID toId, int amount) {
        int fromIndex = findIndexById(fromId);
        int toIndex = findIndexById(toId);
        if (fromIndex == -1 || toIndex == -1) return false;
        User from = data.get(fromIndex);
        User to = data.get(toIndex);
        if (from.getAmount() < amount) return false;
        from.setAmount(from.getAmount() - amount);
        to.setAmount(to.getAmount() + amount);
        return true;
    }

    private synchronized int findIndexById(UUID id) {
        int index = -1;
        int i = 0;
        while (i < data.size() && index == -1) {
            if (data.get(i).getId().equals(id)) index = i;
            i++;
        }
        return index;
    }
}
