package ostask2;
/** One recorded event and the complete visible state at that moment. */
public class SimEvent {
    public final String message;
    public final String resources;
    public final String[] states;

    public SimEvent(String message, String resources, String[] states) {
        this.message = message;
        this.resources = resources;
        this.states = states.clone();
    }
}
