package concurrency.ds;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Demonstrates key operations of {@link CopyOnWriteArrayList}.
 * <p>
 * Thread-safety characteristics:
 * <ul>
 *   <li><b>Every mutation (add, set, remove) creates a fresh copy</b> of the underlying array,
 *       so readers never see a partially modified list. This makes reads lock-free and very fast.</li>
 *   <li>Writes are expensive (O(n) copy) — best suited for <b>read-heavy, write-rare</b> workloads
 *       (e.g., listener/observer lists, configuration caches).</li>
 *   <li>Iterators are <b>snapshot iterators</b> — they walk over the array that existed when the
 *       iterator was created. They never throw {@link java.util.ConcurrentModificationException}
 *       and do NOT reflect subsequent modifications.</li>
 *   <li>Iterator's remove/set/add throw {@link UnsupportedOperationException} since the snapshot is immutable.</li>
 * </ul>
 */
public class CopyOnWriteArrayListOps {

    private final CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    // ==================== Write operations (each one copies the entire array) ====================

    /** add(element) – Appends element. Internally locks, copies array, adds element to copy, swaps reference. */
    public boolean add(String element) {
        return list.add(element);
    }

    /** add(index, element) – Inserts at the given position. O(n) copy + shift. */
    public void addAtIndex(int index, String element) {
        list.add(index, element);
    }

    /** addIfAbsent(element) – Atomic check-then-add. Adds only if the element is not already present. */
    public boolean addIfAbsent(String element) {
        return list.addIfAbsent(element);
    }

    /** set(index, element) – Replaces the element at the given index. Creates a new copy. */
    public String set(int index, String element) {
        return list.set(index, element);
    }

    /** remove(index) – Removes by index. Creates a new copy. */
    public String removeAtIndex(int index) {
        return list.remove(index);
    }

    /** remove(element) – Removes first occurrence. Creates a new copy. Returns true if found. */
    public boolean remove(String element) {
        return list.remove(element);
    }

    // ==================== Read operations (lock-free, no copy needed) ====================

    /** get(index) – Direct array access on the current snapshot. O(1), no locking. */
    public String get(int index) {
        return list.get(index);
    }

    /** contains(element) – Scans the current snapshot. O(n), no locking. */
    public boolean contains(String element) {
        return list.contains(element);
    }

    /** size() – Returns size of the current snapshot. */
    public int size() {
        return list.size();
    }

    /** indexOf(element) – Returns first index, or -1 if absent. Scans snapshot. */
    public int indexOf(String element) {
        return list.indexOf(element);
    }

    // ==================== Snapshot iteration ====================

    /**
     * Returns a snapshot of the list at the moment of invocation.
     * The returned list is a copy — further mutations won't affect it.
     */
    public List<String> snapshot() {
        return List.copyOf(list);
    }

    /**
     * Demonstrates that iterating while another thread modifies the list
     * does NOT throw ConcurrentModificationException.
     * The iterator walks over the snapshot that existed when it was created.
     *
     * @return the elements seen by the iterator (may not include concurrent additions).
     */
    public List<String> iterateWhileMutating() throws InterruptedException {
        list.addAll(List.of("A", "B", "C"));

        List<String> seen = new CopyOnWriteArrayList<>();

        // Start iterating — gets a snapshot of [A, B, C]
        Thread reader = new Thread(() -> {
            for (String s : list) {
                seen.add(s);
                try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        }, "reader-thread");

        // Writer adds "D" while reader is iterating
        Thread writer = new Thread(() -> {
            try { Thread.sleep(25); list.add("D"); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "writer-thread");

        reader.start();
        writer.start();
        reader.join();
        writer.join();

        return seen; // will contain [A, B, C] — "D" was added after the snapshot
    }

    /** Returns the internal list for testing. */
    public CopyOnWriteArrayList<String> getList() {
        return list;
    }
}

