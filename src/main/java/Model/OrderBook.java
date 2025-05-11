package Model;

import Event.BaseEvent;
import Event.OrderCancelled;
import Event.OrderPlaced;
import Event.TradeExecuted;

import java.util.*;

public class OrderBook {
    private final Map<String, OrderPlaced> activeOrders = new HashMap<>();
    private final Set<String> cancelledOrders = new HashSet<>();

    public void apply(BaseEvent event) {
        if (event instanceof OrderPlaced e) {
            activeOrders.put(e.getOrderId(), e);
        } else if (event instanceof OrderCancelled e) {
            activeOrders.remove(e.getOrderId());
            cancelledOrders.add(e.getOrderId());
        } else if (event instanceof TradeExecuted e) {
            activeOrders.remove(e.getBuyOrderId());
            activeOrders.remove(e.getSellOrderId());
        }
    }

    public void rebuildFromEvents(Iterable<BaseEvent> events) {
        activeOrders.clear();
        cancelledOrders.clear();
        for (BaseEvent event : events) {
            apply(event);
        }
    }

    public List<OrderPlaced> getBuyOrdersSorted() {
        return activeOrders.values().stream()
                .filter(OrderPlaced::isBuy)
                .sorted(Comparator.comparingDouble(OrderPlaced::getPrice).reversed())
                .toList();
    }

    public List<OrderPlaced> getSellOrdersSorted() {
        return activeOrders.values().stream()
                .filter(e -> !e.isBuy())
                .sorted(Comparator.comparingDouble(OrderPlaced::getPrice))
                .toList();
    }

    public Map<String, OrderPlaced> getActiveOrders() {
        return Collections.unmodifiableMap(activeOrders);
    }
}
