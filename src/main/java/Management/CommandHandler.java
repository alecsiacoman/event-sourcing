package Management;

import Event.*;
import Model.Account;
import Model.OrderBook;

import java.util.List;
import java.util.UUID;

public class CommandHandler {
    private final EventStore store;
    private final OrderBook orderBook;
    private final Account account;

    public CommandHandler(EventStore store, OrderBook orderBook, Account account) {
        this.store = store;
        this.orderBook = orderBook;
        this.account = account;
    }

    public void placeOrder(String userId, boolean isBuy, double quantity, double price) {
        String orderId = UUID.randomUUID().toString();
        OrderPlaced newOrder = new OrderPlaced(orderId, userId, isBuy, quantity, price);
        store.append(newOrder);

        orderBook.rebuildFromEvents(store.getAllEvents());

        List<OrderPlaced> opposingOrders = isBuy ?
                orderBook.getSellOrdersSorted() :
                orderBook.getBuyOrdersSorted();

        for (OrderPlaced existing : opposingOrders) {
            boolean priceMatch = isBuy
                    ? existing.getPrice() <= price
                    : existing.getPrice() >= price;

            if (priceMatch && existing.getQuantity() == quantity) {
                store.append(new TradeExecuted(
                        isBuy ? orderId : existing.getOrderId(),
                        isBuy ? existing.getOrderId() : orderId,
                        isBuy ? userId : existing.getUserId(),
                        isBuy ? existing.getUserId() : userId,
                        quantity,
                        existing.getPrice()
                ));

                store.append(new FundsCredited(
                        isBuy ? existing.getUserId() : userId,
                        quantity * existing.getPrice()
                ));

                if (isBuy) {
                    store.append(new FundsDebited(userId, quantity * price));
                }

                break;
            }
        }
    }


    public void cancelOrder(String orderId, String userId) {
        store.append(new OrderCancelled(orderId, userId));
        orderBook.rebuildFromEvents(store.getAllEvents());
    }

    public void depositFunds(String userId, double amount) {
        store.append(new FundsCredited(userId, amount));
    }

    public void withdrawFunds(String userId, double amount) {
        store.append(new FundsDebited(userId, amount));
    }
}
