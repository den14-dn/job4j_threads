package cas;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

public class CASCountTest {
    @Test
    public void whenIncrementSecondly() {
        CASCount casCount = new CASCount(2);
        casCount.increment();
        casCount.increment();
        assertThat(casCount.get(), is(4));
    }

    @Test
    public void whenIncrementInThreads() throws InterruptedException {
        CASCount casCount = new CASCount(0);
        Thread thread1 = new Thread(
                () -> {
                    for (int i = 0; i < 3; i++) {
                        casCount.increment();
                    }
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    for (int i = 0; i < 3; i++) {
                        casCount.increment();
                    }
                }
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(casCount.get(), is(6));
    }
}
