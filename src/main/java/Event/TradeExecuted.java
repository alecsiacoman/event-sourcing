package Event;

import java.time.LocalDateTime;

public class TradeExecuted extends BaseEvent{
    private final String buyOrderId;
    private final String sellOrderId;
    private final String buyerId;
    private final String sellerId;
    private final double quantity;
    private final double price;

    public TradeExecuted(String buyOrderId, String sellOrderId, String buyerId, String sellerId, double quantity, double price) {
        super(LocalDateTime.now());
        this.buyOrderId = buyOrderId;
        this.sellOrderId = sellOrderId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.quantity = quantity;
        this.price = price;
    }

    public String getBuyOrderId() { return buyOrderId; }
    public String getSellOrderId() { return sellOrderId; }
    public String getBuyerId() { return buyerId; }
    public String getSellerId() { return sellerId; }
    public double getQuantity() { return quantity; }
    public double getPrice() { return price; }
}
