package ostask2;
public class DiningPhilosophersMonitor {
    private static final int N = 4;
    private static final int MEALS = 2;
    private static final int THINKING = 0, HUNGRY = 1, EATING = 2;
    private final int[] states = new int[N];
    private final SimulationLog log;

    private DiningPhilosophersMonitor() {
        String[] labels = new String[N];
        for (int i = 0; i < N; i++) {
            labels[i] = "Philosopher " + (i + 1);
            states[i] = THINKING;
        }
        log = new SimulationLog(labels, forkStatus());
    }

    private int leftNeighbour(int i) { return (i + N - 1) % N; }
    private int rightNeighbour(int i) { return (i + 1) % N; }

    /** A philosopher can eat only when both neighbours are not eating. */
    private synchronized void test(int i) {
        if (states[i] == HUNGRY
                && states[leftNeighbour(i)] != EATING
                && states[rightNeighbour(i)] != EATING) {
            states[i] = EATING;
            log.record(i, "Eating", "Philosopher " + (i + 1)
                    + " can eat because both neighbours are not eating.", forkStatus());
            notifyAll();
        }
    }

    private synchronized void pickUpForks(int i) throws InterruptedException {
        states[i] = HUNGRY;
        log.record(i, "Hungry / waiting", "Philosopher " + (i + 1) + " became hungry.", forkStatus());
        test(i);
        while (states[i] != EATING) wait();
    }

    private synchronized void putDownForks(int i) {
        states[i] = THINKING;
        log.record(i, "Releasing forks", "Philosopher " + (i + 1)
                + " finished eating and released both forks.", forkStatus());
        test(leftNeighbour(i));
        test(rightNeighbour(i));
        notifyAll();
    }

    /** Derive fork ownership from the actual monitor state. */
    private String forkStatus() {
        String[] owners = {"available", "available", "available", "available"};
        for (int p = 0; p < N; p++) {
            if (states[p] == EATING) {
                owners[p] = "Philosopher " + (p + 1);
                owners[(p + 1) % N] = "Philosopher " + (p + 1);
            }
        }
        StringBuilder b = new StringBuilder("Fork ownership:\n");
        for (int i = 0; i < N; i++) b.append("Fork ").append(i + 1).append(": ")
                .append(owners[i]).append(i == N - 1 ? "" : "\n");
        return b.toString();
    }

    private void philosopher(int id) {
        String name = "Philosopher " + (id + 1);
        try {
            for (int meal = 1; meal <= MEALS; meal++) {
                log.record(id, "Thinking", name + " is thinking (round " + meal + ").", forkStatus());
                Thread.sleep(260 + id * 75L);
                pickUpForks(id);
                Thread.sleep(330 + id * 60L);
                putDownForks(id);
            }
            log.record(id, "Finished", name + " finished both meals.", forkStatus());
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            log.record(id, "Interrupted", name + " was interrupted.", forkStatus());
        }
    }

    public static void main(String[] args) throws Exception {
        DiningPhilosophersMonitor app = new DiningPhilosophersMonitor();
        Thread[] threads = new Thread[N];
        for (int i = 0; i < N; i++) {
            final int id = i;
            threads[i] = new Thread(() -> app.philosopher(id), "Philosopher-" + (i + 1));
        }
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
        app.log.writeHtml("generated_html/dining_philosophers_monitor.html",
                "Dining Philosophers — Monitor Solution",
                "Exactly four philosophers use a Java monitor, condition waiting, and neighbour checks before eating.");
    }
}
