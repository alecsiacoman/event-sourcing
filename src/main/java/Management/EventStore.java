package Management;

import Event.BaseEvent;
import java.util.ArrayList;
import java.util.List;

public class EventStore {
    private final List<BaseEvent> events = new ArrayList<>();

    public void append(BaseEvent event) {
        events.add(event);
    }

    public List<BaseEvent> getAllEvents() {
        return new ArrayList<>(events);
    }

    public void clear() {
        events.clear();
    }
}
