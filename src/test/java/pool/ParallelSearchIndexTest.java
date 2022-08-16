package pool;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

public class ParallelSearchIndexTest {
    @Test
    public void whenSearchNothing() {
        User model = new User("Test", "test@mail.com");
        User[] users = {
                new User("Иванов", "ivanov@mail.com"),
                new User("Петров", "petrov@mail.com")
        };
        int rst = ParallelSearchIndex.search(users, model);
        assertThat(rst, is(-1));
    }

    @Test
    public void whenSearchOnIndex0() {
        User model = new User("Test", "test@mail.com");
        User[] users = {
                model,
                new User("Иванов", "ivanov@mail.com"),
                new User("Петров", "petrov@mail.com")
        };
        int rst = ParallelSearchIndex.search(users, model);
        assertThat(rst, is(0));
    }

    @Test
    public void whenSearchSuccess() {
        User model = new User("Test", "test@mail.com");
        User[] users = {
                new User("Пользователь1", "user1@mail.com"),
                new User("Пользователь2", "user2@mail.com"),
                new User("Пользователь3", "user3@mail.com"),
                new User("Пользователь4", "user4@mail.com"),
                new User("Пользователь5", "user5@mail.com"),
                new User("Пользователь6", "user6@mail.com"),
                new User("Пользователь7", "user7@mail.com"),
                new User("Пользователь8", "user8@mail.com"),
                new User("Пользователь9", "user9@mail.com"),
                new User("Пользователь10", "user10@mail.com"),
                new User("Пользователь11", "user11@mail.com"),
                new User("Пользователь12", "user12@mail.com"),
                new User("Пользователь13", "user13@mail.com"),
                model,
                new User("Пользователь15", "user15@mail.com")
        };
        int rst = ParallelSearchIndex.search(users, model);
        assertThat(rst, is(13));
    }
}
