package concurrent.transfer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private Map<Integer, User> users = new HashMap<>();

    public synchronized boolean add(User user) {
        return users.putIfAbsent(user.getId(), user) == user;
    }

    public synchronized boolean update(User user) {
        return users.replace(user.getId(), users.get(user.getId()), user);
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rst = false;
        User fromUser = users.get(fromId);
        User toUser = users.get(toId);
        if (fromUser != null && toUser != null
                && fromUser.getAmount() >= amount) {
            fromUser.setAmount(toUser.getAmount() - amount);
            toUser.setAmount(toUser.getAmount() + amount);
            rst = true;
        }
        return rst;
    }

    public static void main(String[] args) {
        UserStorage store = new UserStorage();

        store.add(new User(1, 100));
        store.add(new User(2, 200));

        store.transfer(1, 2, 50);
    }
}
