package cache;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

public class CacheTest {
    @Test
    public void whenAddSuccess() {
        Cache cache = new Cache();
        assertTrue(cache.add(new Base(1, 0)));
    }

    @Test
    public void whenAddNotSuccess() {
        Base base = new Base(1, 0);
        Cache cache = new Cache();
        cache.add(base);
        assertFalse(cache.add(base));
    }

    @Test
    public void whenUpdateSuccess() {
        Base base = new Base(1, 0);
        Cache cache = new Cache();
        cache.add(base);
        assertTrue(cache.update(base));
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdateNotSuccess() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0);
        Base base2 = new Base(1, 1);
        cache.add(base1);
        cache.update(base2);
    }
}
