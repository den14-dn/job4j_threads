package wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue;
    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
        queue = new LinkedList<>();
    }

    public synchronized void offer(T value) {
        conditionalWait(queue.size() == limit);
        queue.offer(value);
        this.notify();
    }

    public synchronized T poll() {
        conditionalWait(queue.isEmpty());
        T value = queue.poll();
        this.notify();
        return value;
    }

    private synchronized void conditionalWait(boolean condition) {
        while (!Thread.currentThread().isInterrupted() && condition) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
