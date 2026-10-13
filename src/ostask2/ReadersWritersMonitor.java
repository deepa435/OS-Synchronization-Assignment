package ostask2;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Readers-Writers monitor implemented with synchronized methods, wait and notifyAll.
 * Waiting writers get priority over newly arriving readers.
 */
public class ReadersWritersMonitor {
    private static final int READER_COUNT = 3;
    private static final int WRITER_COUNT = 2;
    private int activeReaders = 0;
    private int waitingWriters = 0;
    private boolean writerActive = false;
    private final SimulationLog log;

    private ReadersWritersMonitor() {
        String[] labels = new String[READER_COUNT + WRITER_COUNT];
        for (int i = 0; i < READER_COUNT; i++) labels[i] = "Reader " + (i + 1);
        for (int i = 0; i < WRITER_COUNT; i++) labels[READER_COUNT + i] = "Writer " + (i + 1);
        log = new SimulationLog(labels, "Shared resource is available; no reader or writer is active.");
    }

    private String resourceStatus() {
        return "Shared resource: " + (writerActive ? "being written by a writer" :
                (activeReaders > 0 ? "being read by " + activeReaders + " reader(s)" : "available"))
                + "\nActive readers: " + activeReaders + "\nWaiting writers: " + waitingWriters
                + "\nWriter active: " + writerActive;
    }

    private synchronized void beginRead(int id) throws InterruptedException {
        log.record(id, "Waiting", "Reader " + (id + 1) + " is waiting to read.", resourceStatus());
        while (writerActive || waitingWriters > 0) wait();
        activeReaders++;
        log.record(id, "Reading", "Reader " + (id + 1) + " entered the read section.", resourceStatus());
    }

    private synchronized void endRead(int id) {
        activeReaders--;
        log.record(id, "Finished", "Reader " + (id + 1) + " left the read section.", resourceStatus());
        if (activeReaders == 0) notifyAll();
    }

    private synchronized void beginWrite(int id) throws InterruptedException {
        int actor = READER_COUNT + id;
        waitingWriters++;
        log.record(actor, "Waiting", "Writer " + (id + 1) + " is waiting to write.", resourceStatus());
        try {
            while (writerActive || activeReaders > 0) wait();
        } finally {
            waitingWriters--;
        }
        writerActive = true;
        log.record(actor, "Writing", "Writer " + (id + 1) + " entered the write section exclusively.", resourceStatus());
    }

    private synchronized void endWrite(int id) {
        writerActive = false;
        log.record(READER_COUNT + id, "Finished",
                "Writer " + (id + 1) + " finished writing and released the resource.", resourceStatus());
        notifyAll();
    }

    private void reader(int id) {
        try {
            beginRead(id);
            Thread.sleep(400 + id * 140L);
            endRead(id);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            log.record(id, "Interrupted", "Reader " + (id + 1) + " was interrupted.", resourceStatus());
        }
    }

    private void writer(int id) {
        int actor = READER_COUNT + id;
        try {
            beginWrite(id);
            Thread.sleep(550 + id * 130L);
            endWrite(id);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            log.record(actor, "Interrupted", "Writer " + (id + 1) + " was interrupted.", resourceStatus());
        }
    }

    public static void main(String[] args) throws Exception {
        ReadersWritersMonitor app = new ReadersWritersMonitor();
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
        app.log.writeHtml("generated_html/readers_writers_monitor.html",
                "Readers-Writers — Monitor Solution",
                "A Java monitor using synchronized methods, wait and notifyAll; waiting writers block new readers.");
    }
}
