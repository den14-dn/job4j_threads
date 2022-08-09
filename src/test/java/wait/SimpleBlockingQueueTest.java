package wait;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

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
    public void whenConsumerBreakTheadAfterOfProducer() throws InterruptedException {
        CopyOnWriteArrayList<Integer> rst = new CopyOnWriteArrayList<>();
        SimpleBlockingQueue<Integer> simpleQueue = new SimpleBlockingQueue<>(5);

        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 3; i++) {
                        try {
                            simpleQueue.offer(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Thread consumer = new Thread(
                () -> {
                    while (!simpleQueue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            rst.add(simpleQueue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                });
        producer.start();
        consumer.start();

        producer.join();
        consumer.interrupt();
        consumer.join();

        assertThat(rst, is(Arrays.asList(0, 1, 2)));
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);

        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        producer.start();

        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();

        producer.join();

        consumer.interrupt();
        consumer.join();

        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }
}
