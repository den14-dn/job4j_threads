package concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final String target;
    private final int speed;

    public Wget(String url, String target, int speed) {
        this.url = url;
        this.target = target;
        this.speed = speed;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(target)) {
                byte[] dataBuffer = new byte[1024];
                int bytesRead;

                long leftPoint = System.currentTimeMillis();

                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);

                    long dif = System.currentTimeMillis() - leftPoint;
                    int speedLoading = (int) (1024 / (dif / 1000));
                    if (speedLoading < speed) {
                        Thread.sleep((speed - speedLoading) * 1000);
                    }
                    leftPoint = System.currentTimeMillis();
                }
            } catch (Exception e) {
                Thread.currentThread().interrupted();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        String target = args[1];
        int speed = Integer.parseInt(args[2]);
        Thread wget = new Thread(new Wget(url, target, speed));
        wget.start();
        wget.join();
    }
}
