

package ostask2;

import java.util.concurrent.Semaphore;

/**
 * Four Dining Philosophers using semaphores.
 * The room semaphore permits at most three philosophers to compete for forks,
 * preventing the circular-wait deadlock that can occur when all four hold one fork.
 */
public class DiningPhilosophersSemaphore {
    private static final int N = 4;
    private static final int MEALS = 2;
    private final Semaphore room = new Semaphore(N - 1, true);
    private final Semaphore[] forks = new Semaphore[N];
    private final String[] forkOwners = new String[N];
    private final SimulationLog log;

    private DiningPhilosophersSemaphore() {
        String[] labels = new String[N];
        for (int i = 0; i < N; i++) {
            labels[i] = "Philosopher " + (i + 1);
            forks[i] = new Semaphore(1, true);
            forkOwners[i] = "available";
        }
        log = new SimulationLog(labels, "All four forks are available.");
    }

    private synchronized String forkStatus() {
        StringBuilder b = new StringBuilder("Fork ownership:\n");
        for (int i = 0; i < N; i++) b.append("Fork ").append(i + 1).append(": ")
                .append(forkOwners[i]).append(i == N - 1 ? "" : "\n");
        return b.toString();
    }

    private void setForkOwner(int fork, String owner) {
        synchronized (this) { forkOwners[fork] = owner; }
    }

    private void philosopher(int id) {
        String name = "Philosopher " + (id + 1);
        int left = id;
        int right = (id + 1) % N;
        try {
            for (int meal = 1; meal <= MEALS; meal++) {
                log.record(id, "Thinking", name + " is thinking (round " + meal + ").", forkStatus());
                Thread.sleep(250 + id * 80L);
                log.record(id, "Hungry / waiting", name + " is hungry and waiting for a seat.", forkStatus());
                room.acquire();

                forks[left].acquire();
                setForkOwner(left, name);
                log.record(id, "Acquiring forks", name + " acquired fork " + (left + 1) + ".", forkStatus());

                forks[right].acquire();
                setForkOwner(right, name);
                log.record(id, "Eating", name + " acquired fork " + (right + 1) + " and is eating.", forkStatus());
                Thread.sleep(350 + id * 70L);

                setForkOwner(right, "available");
                forks[right].release();
                log.record(id, "Releasing forks", name + " released fork " + (right + 1) + ".", forkStatus());
                setForkOwner(left, "available");
                forks[left].release();
                room.release();
                log.record(id, "Thinking", name + " released fork " + (left + 1) + " and left the table.", forkStatus());
            }
            log.record(id, "Finished", name + " finished both meals.", forkStatus());
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            log.record(id, "Interrupted", name + " was interrupted.", forkStatus());
        }
    }

    public static void main(String[] args) throws Exception {
        DiningPhilosophersSemaphore app = new DiningPhilosophersSemaphore();
        Thread[] threads = new Thread[N];
        for (int i = 0; i < N; i++) {
            final int id = i;
            threads[i] = new Thread(() -> app.philosopher(id), "Philosopher-" + (i + 1));
        }
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
        app.log.writeHtml("generated_html/dining_philosophers_semaphore.html",
                "Dining Philosophers — Semaphore Solution",
                "Exactly four philosophers use four semaphores for forks and a three-seat room semaphore to prevent deadlock.");
    }
}
