package ostask2;

import java.util.concurrent.Semaphore;

/**
 * Readers-Writers using semaphores.
 * roomEmpty protects the shared resource; mutex protects readCount;
 * turnstile prevents new readers from continually bypassing a waiting writer.
 */
public class ReadersWritersSemaphore {
    private static final int READER_COUNT = 3;
    private static final int WRITER_COUNT = 2;

    private final Semaphore roomEmpty = new Semaphore(1, true);
    private final Semaphore mutex = new Semaphore(1, true);
    private final Semaphore turnstile = new Semaphore(1, true);
    private int readCount = 0;
    private volatile boolean writerActive = false;
    private final SimulationLog log;

    private ReadersWritersSemaphore() {
        String[] labels = new String[READER_COUNT + WRITER_COUNT];
        for (int i = 0; i < READER_COUNT; i++) labels[i] = "Reader " + (i + 1);
        for (int i = 0; i < WRITER_COUNT; i++) labels[READER_COUNT + i] = "Writer " + (i + 1);
        log = new SimulationLog(labels, "Shared resource is available; no reader or writer is active.");
    }

    private String resourceStatus() {
        return "Shared resource: " + (writerActive ? "being written by a writer" :
                (readCount > 0 ? "being read by " + readCount + " reader(s)" : "available"))
                + "\nActive readers: " + readCount + "\nWriter active: " + writerActive;
    }

    private void reader(int id) {
        String name = "Reader " + (id + 1);
        try {
            log.record(id, "Waiting", name + " is waiting to read.", resourceStatus());
            turnstile.acquire();
            mutex.acquire();
            readCount++;
            if (readCount == 1) roomEmpty.acquire();
            log.record(id, "Reading", name + " entered the read section.", resourceStatus());
            mutex.release();
            turnstile.release();

            Thread.sleep(450 + id * 120L);

            mutex.acquire();
            readCount--;
            if (readCount == 0) roomEmpty.release();
            log.record(id, "Finished", name + " left the read section.", resourceStatus());
            mutex.release();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            log.record(id, "Interrupted", name + " was interrupted.", resourceStatus());
        }
    }

    private void writer(int id) {
        int actor = READER_COUNT + id;
        String name = "Writer " + (id + 1);
        try {
            log.record(actor, "Waiting", name + " is waiting to write.", resourceStatus());
            turnstile.acquire();
            roomEmpty.acquire();
            turnstile.release();

            writerActive = true;
            log.record(actor, "Writing", name + " entered the write section exclusively.", resourceStatus());
            Thread.sleep(600 + id * 150L);
            writerActive = false;
            roomEmpty.release();
            log.record(actor, "Finished", name + " finished writing and released the resource.", resourceStatus());
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            writerActive = false;
            log.record(actor, "Interrupted", name + " was interrupted.", resourceStatus());
        }
    }

    public static void main(String[] args) throws Exception {
        ReadersWritersSemaphore app = new ReadersWritersSemaphore();
        Thread[] threads = new Thread[READER_COUNT + WRITER_COUNT];
        for (int i = 0; i < READER_COUNT; i++) {
            final int id = i;
            threads[i] = new Thread(() -> app.reader(id), "Reader-" + (i + 1));
        }
        for (int i = 0; i < WRITER_COUNT; i++) {
            final int id = i;
            threads[READER_COUNT + i] = new Thread(() -> app.writer(id), "Writer-" + (i + 1));
        }
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
        app.log.writeHtml("generated_html/readers_writers_semaphore.html",
                "Readers-Writers — Semaphore Solution",
                "A semaphore-based solution: multiple readers may read together, but writing is exclusive.");
    }
}
