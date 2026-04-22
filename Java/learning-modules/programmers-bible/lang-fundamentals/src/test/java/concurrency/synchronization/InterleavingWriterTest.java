package concurrency.synchronization;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InterleavingWriterTest {

    /** Expected output: each letter A–Z on its own line, in order. */
    private static final String EXPECTED = IntStream.range(0, 26)
            .mapToObj(i -> String.valueOf((char) ('A' + i)) + String.valueOf(i))
            .collect(Collectors.joining());

    // ===================== Semaphore tests =====================

    private static final String baseDir = ".";


    @Test
    void semaphoreWriterProducesAlphabetsInOrder() throws IOException, InterruptedException {
        Path file = Path.of(baseDir, "semaphore_count.txt");

        new InterleavingSemaphoreWriter().writeAlphabets(file);

        List<String> lines = Files.readAllLines(file);
        assertEquals(EXPECTED, lines.get(0), "Semaphore writer should produce A–Z in order");
    }

    @Test
    void semaphoreWriterWrites26Characters() throws IOException, InterruptedException {
        Path file = Path.of(baseDir, "semaphore_count.txt");

        new InterleavingSemaphoreWriter().writeAlphabets(file);

        assertEquals(26, Files.readAllLines(file).get(0).length());
    }

    // ===================== Lock tests =====================

    @Test
    void lockWriterProducesAlphabetsInOrder() throws IOException, InterruptedException {
        Path file = Path.of(baseDir, "lock_output.txt");

        new InterleavingLockWriter().writeAlphabets(file);

        List<String> lines = Files.readAllLines(file);
        assertEquals(EXPECTED, lines.get(0), "Lock writer should produce A–Z in order");
    }

    @Test
    void lockWriterWrites26Lines() throws IOException, InterruptedException {
        Path file = Path.of(baseDir, "lock_output.txt");

        new InterleavingLockWriter().writeAlphabets(file);

        assertEquals(26, Files.readAllLines(file).get(0).length());
    }

    // ===================== Both produce identical output =====================

    @Test
    void bothWritersProduceSameOutput() throws IOException, InterruptedException {
        Path semFile = Path.of(baseDir, "semaphore.txt");
        Path lockFile = Path.of(baseDir, "lock.txt");

        new InterleavingSemaphoreWriter().writeAlphabets(semFile);
        new InterleavingLockWriter().writeAlphabets(lockFile);

        assertEquals(Files.readAllLines(semFile), Files.readAllLines(lockFile),
                "Both implementations should produce identical interleaved output");
    }
}

