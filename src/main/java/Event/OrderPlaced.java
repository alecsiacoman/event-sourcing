package Event;

import java.time.LocalDateTime;

public class OrderPlaced extends BaseEvent {
    private final String orderId;
    private final String userId;
    private final boolean isBuy;
    private final double quantity;
    private final double price;

    public OrderPlaced(String orderId, String userId, boolean isBuy, double quantity, double price) {
        super(LocalDateTime.now());
        this.orderId = orderId;
        this.userId = userId;
        this.isBuy = isBuy;
        this.quantity = quantity;
        this.price = price;
    }

    public String getOrderId() { return orderId; }
    public String getUserId() { return userId; }
    public boolean isBuy() { return isBuy; }
    public double getQuantity() { return quantity; }
    public double getPrice() { return price; }
}
