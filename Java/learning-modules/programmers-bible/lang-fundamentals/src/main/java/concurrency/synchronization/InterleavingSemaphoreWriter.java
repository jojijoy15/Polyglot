package concurrency.synchronization;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Semaphore;

/**
 * Two threads interleave alphabets (A–Z) into a file using Semaphores.
 * <p>
 * Thread-1 writes odd-positioned letters  : A, C, E, G, ...
 * Thread-2 writes even-positioned letters  : B, D, F, H, ...
 * <p>
 * Result in the file: A B C D E F ... Z  (each on a new line).
 * <p>
 * How it works:
 * - semaphoreOdd  starts with 1 permit  → Thread-1 goes first.
 * - semaphoreEven starts with 0 permits → Thread-2 waits until Thread-1 signals.
 * - After Thread-1 writes, it releases semaphoreEven so Thread-2 can proceed, and vice-versa.
 */
public class InterleavingSemaphoreWriter {

    private final Semaphore semaphoreOdd = new Semaphore(1);  // odd thread starts first
    private final Semaphore semaphoreEven = new Semaphore(0); // even thread waits

    /**
     * Writes interleaved alphabets A–Z into the given file using two threads.
     */
    public void writeAlphabets(Path filePath) throws IOException, InterruptedException {
        // Ensure parent dirs exist
        if (filePath.getParent() != null) {
            Files.createDirectories(filePath.getParent());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {

            // Thread-1: writes A(0), C(2), E(4), ... Y(24)
            Thread oddThread = new Thread(() -> {
                try {
                    for (int i = 0; i < 26; i += 2) {
                        semaphoreOdd.acquire();         // wait for turn
                        writer.write((char) ('A' + i));
//                        writer.write('0');
                        semaphoreEven.release();        // signal even thread
                    }
                } catch (InterruptedException | IOException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }, "odd-thread");

            // Thread-2: writes B(1), D(3), F(5), ... Z(25)
            Thread evenThread = new Thread(() -> {
                try {
                    for (int i = 1; i < 26; i += 2) {
                        semaphoreEven.acquire();        // wait for turn
                        writer.write((char) ('A' + i));
//                        writer.write('1');
                        semaphoreOdd.release();         // signal odd thread
                    }
                } catch (InterruptedException | IOException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }, "even-thread");

            oddThread.start();
            evenThread.start();

            oddThread.join();
            evenThread.join();
        }
    }
}

