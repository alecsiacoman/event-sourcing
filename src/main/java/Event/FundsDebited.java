package Event;

import java.time.LocalDateTime;

public class FundsDebited extends BaseEvent{
    private final String userId;
    private final double amount;

    public FundsDebited(String userId, double amount) {
        super(LocalDateTime.now());
        this.userId = userId;
        this.amount = amount;
    }

    public String getUserId() { return userId; }
    public double getAmount() { return amount; }
}
