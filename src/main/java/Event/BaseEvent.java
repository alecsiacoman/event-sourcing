package Event;

import java.time.LocalDateTime;

public class BaseEvent {
    private final LocalDateTime timestamp;

    public BaseEvent(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getTimestamp() {
        return null;
    }
}
