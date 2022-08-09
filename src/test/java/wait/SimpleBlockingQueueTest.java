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
                    try {
                        simpleQueue.offer(3);
                        simpleQueue.offer(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
        Thread consumer = new Thread(
                () -> {
                    try {
                        simpleQueue.poll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
                    try {
                        simpleQueue.offer(3);
                        simpleQueue.offer(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
        Thread consumer = new Thread(
                () -> {
                    try {
                        simpleQueue.poll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
                        try {
                            simpleQueue.offer(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Thread consumer = new Thread(
                () -> {
                    while (!simpleQueue.isEmpty()) {
                        int value = 0;
                        try {
                            value = simpleQueue.poll();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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
