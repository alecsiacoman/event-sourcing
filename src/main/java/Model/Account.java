package Model;

import Event.BaseEvent;
import Event.FundsCredited;
import Event.FundsDebited;

import java.util.HashMap;
import java.util.Map;

public class Account {
    private final Map<String, Double> balances = new HashMap<>();

    public void apply(BaseEvent event) {
        if (event instanceof FundsCredited e) {
            balances.merge(e.getUserId(), e.getAmount(), Double::sum);
        } else if (event instanceof FundsDebited e) {
            balances.merge(e.getUserId(), -e.getAmount(), Double::sum);
        }
    }

    public double getBalance(String userId) {
        return balances.getOrDefault(userId, 0.0);
    }

    public void rebuildFromEvents(Iterable<BaseEvent> events) {
        balances.clear();
        for (BaseEvent event : events) {
            apply(event);
        }
    }
}
