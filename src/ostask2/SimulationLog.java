package ostask2;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Records real state changes made by the synchronization program.
 * The HTML file is created from these recorded events after the threads finish.
 */
public class SimulationLog {
    private final String[] labels;
    private final String[] states;
    private final List<SimEvent> events = new ArrayList<>();

    public SimulationLog(String[] labels, String initialResources) {
        this.labels = labels.clone();
        this.states = new String[labels.length];
        for (int i = 0; i < states.length; i++) {
            states[i] = "Not started";
        }
        events.add(new SimEvent("Simulation created.", initialResources, states));
    }

    public synchronized void record(int actorIndex, String newState,
                                    String message, String resources) {
        if (actorIndex >= 0 && actorIndex < states.length) {
            states[actorIndex] = newState;
        }
        events.add(new SimEvent(message, resources, states));
    }

    public synchronized void note(String message, String resources) {
        events.add(new SimEvent(message, resources, states));
    }

    public synchronized void writeHtml(String outputPath, String title, String description)
            throws IOException {
        Path path = Paths.get(outputPath);
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }
        String html = HtmlSimulation.create(title, description, labels, events);
        Files.write(path, html.getBytes(StandardCharsets.UTF_8));
        System.out.println("HTML simulation generated: " + path.toAbsolutePath());
        System.out.println("Recorded events: " + events.size());
    }
}
