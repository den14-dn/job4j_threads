package concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        String[] process = new String[] {"\\", "|", "/"};
        int index = 0;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\r load: " + process[index]);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            index++;
            if (index == process.length) {
                index = 0;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(50000);
        progress.interrupt();
    }
}
