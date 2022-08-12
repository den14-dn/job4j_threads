package cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(
                model.getId(),
                (key, value) -> {
                    if (model.getVersion() != value.getVersion()) {
                        throw new OptimisticException("Versions are not equal");
                    }
                    Base base = new Base(model.getId(), model.getVersion() + 1);
                    base.setName(model.getName());
                    return base;
                }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public static void main(String[] args) {
        Map<Integer, Base> map = new HashMap<>();
        Base base = new Base(1, 0);
        map.put(base.getId(), base);

        Base user1 = map.get(1);
        user1.setName("User 1");

        Base user2 = map.get(1);
        user1.setName("User 2");

        map.put(user1.getId(), user1);
        map.put(user2.getId(), user2);

        System.out.println(map);
    }
}
