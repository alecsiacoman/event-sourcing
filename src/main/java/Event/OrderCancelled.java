package Event;

import java.time.LocalDateTime;

public class OrderCancelled extends BaseEvent{
    private final String orderId;
    private final String userId;

    public OrderCancelled(String orderId, String userId) {
        super(LocalDateTime.now());
        this.orderId = orderId;
        this.userId = userId;
    }

    public String getOrderId() { return orderId; }
    public String getUserId() { return userId; }
}
