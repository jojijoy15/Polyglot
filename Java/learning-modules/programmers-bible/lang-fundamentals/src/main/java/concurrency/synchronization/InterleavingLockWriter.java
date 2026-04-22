package concurrency.synchronization;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Two threads interleave alphabets (A–Z) into a file using ReentrantLock + Conditions.
 * <p>
 * Thread-1 writes odd-positioned letters  : A, C, E, G, ...
 * Thread-2 writes even-positioned letters  : B, D, F, H, ...
 * <p>
 * Result in the file: A B C D E F ... Z  (each on a new line).
 * <p>
 * How it works:
 * - A shared boolean {@code oddTurn} indicates whose turn it is.
 * - Each thread acquires the lock, checks the flag; if it's not its turn it awaits on its Condition.
 * - After writing, it flips the flag and signals the other thread's Condition.
 */
public class InterleavingLockWriter {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition oddCondition = lock.newCondition();
    private final Condition evenCondition = lock.newCondition();
    private volatile boolean oddTurn = true; // odd thread goes first

    /**
     * Writes interleaved alphabets A–Z into the given file using two threads.
     */
    public void writeAlphabets(Path filePath) throws IOException, InterruptedException {
        if (filePath.getParent() != null) {
            Files.createDirectories(filePath.getParent());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {

            // Thread-1: writes A(0), C(2), E(4), ... Y(24)
            Thread oddThread = new Thread(() -> {
                try {
                    for (int i = 0; i < 26; i += 2) {
                        lock.lock();
                        try {
                            while (!oddTurn) {              // not my turn → wait
                                oddCondition.await();
                            }
                            writer.write((char) ('A' + i));
//                            writer.write('0');

                            oddTurn = false;                // hand over to even thread
                            evenCondition.signal();         // wake up even thread
                        } finally {
                            lock.unlock();
                        }
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
                        lock.lock();
                        try {
                            while (oddTurn) {               // not my turn → wait
                                evenCondition.await();
                            }
                            writer.write((char) ('A' + i));
//                            writer.write('1');
                            oddTurn = true;                 // hand over to odd thread
                            oddCondition.signal();          // wake up odd thread
                        } finally {
                            lock.unlock();
                        }
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

