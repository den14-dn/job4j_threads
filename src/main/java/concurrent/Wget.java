package concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final String target;
    private final int speed;

    public Wget(String url, String target, int speed) {
        this.url = url;
        this.speed = speed;
        this.target = target;
    }

    @Override
    public void run() {
        /*while (!Thread.currentThread().isInterrupted()) {
            try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(target)) {

                long readed = 0L;
                byte[] dataBuffer = new byte[1024 * readed];
                int bytesRead;

                long leftPoint = System.currentTimeMillis();

                try {
                    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);

                        long dif = System.currentTimeMillis() - leftPoint;
                        int speedLoading = (int) (1024 / (dif / 1000));
                        if (speedLoading < speed) {
                            Thread.sleep((speed - speedLoading) * 1000);
                        }
                        leftPoint = System.currentTimeMillis();
                    }
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupted();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        int speed = Integer.parseInt(args[2]);
        Thread wget = new Thread(new Wget(args[0], args[1], speed));
        wget.start();
    }

    private static void validate(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("");
        }
    }
}
