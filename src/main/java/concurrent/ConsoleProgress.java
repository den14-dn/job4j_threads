package concurrent;

public class ConsoleProgress implements Runnable {
    private String[] process = new String[] {"\\", "|", "/"};

    @Override
    public void run() {
        int index = 0;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\r load: " + process[index]);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
            index = ++index < process.length ? index : 0;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1000);
        progress.interrupt();
    }
}
