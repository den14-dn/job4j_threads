package concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        System.out.println(first.getState());
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getState());
        }
        while (second.getState() != Thread.State.TERMINATED) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(first.getState());
        System.out.println("Job completed");
    }
}
