package wait;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {
    @Test
    public void whenProducerFirst() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleQueue = new SimpleBlockingQueue<>(5);

        Thread producer = new Thread(
                () -> {
                    simpleQueue.offer(3);
                    simpleQueue.offer(5);
                });
        Thread consumer = new Thread(
                () -> {
                    simpleQueue.poll();
                });
        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        int rst = simpleQueue.poll();

        assertEquals(rst, 5);
    }

    @Test
    public void whenProducerLast() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleQueue = new SimpleBlockingQueue<>(5);

        Thread producer = new Thread(
                () -> {
                    simpleQueue.offer(3);
                    simpleQueue.offer(5);
                });
        Thread consumer = new Thread(
                () -> {
                    simpleQueue.poll();
                });
        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        int rst = simpleQueue.poll();

        assertEquals(rst, 5);
    }

    @Test
    public void when() throws InterruptedException {
        List<Integer> rst = new ArrayList<>();
        SimpleBlockingQueue<Integer> simpleQueue = new SimpleBlockingQueue<>(5);

        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        simpleQueue.offer(i);
                    }
                });
        Thread consumer = new Thread(
                () -> {
                    while (!simpleQueue.isEmpty()) {
                        int value = simpleQueue.poll();
                        rst.add(value);
                    }
                });
        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        assertThat(rst, is(Arrays.asList(0, 1, 2, 3, 4)));
    }

}
