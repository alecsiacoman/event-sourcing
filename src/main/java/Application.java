import Event.OrderPlaced;
import Management.CommandHandler;
import Management.EventStore;
import Model.Account;
import Model.OrderBook;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        EventStore store = new EventStore();
        OrderBook orderBook = new OrderBook();
        Account account = new Account();
        CommandHandler handler = new CommandHandler(store, orderBook, account);

        handler.depositFunds("alecsia", 1000);
        handler.depositFunds("alexandra", 500);

        handler.placeOrder("alecsia", false, 10, 50);
        handler.placeOrder("alexandra", true, 10, 50);

        handler.placeOrder("alexandra", false, 5, 60);
        String lastOrderId = String.valueOf(orderBook.getActiveOrders().values().stream()
                .filter(o -> o.getUserId().equals("alexandra") && o.getPrice() == 60)
                .map(OrderPlaced::getOrderId)
                .findFirst());
        handler.cancelOrder(lastOrderId, "alexandra");

        for (var event : store.getAllEvents()) {
            orderBook.apply(event);
            account.apply(event);
        }

        System.out.println("===== Event Log =====");
        store.getAllEvents().forEach(System.out::println);

        System.out.println("alecsia balance: " + account.getBalance("alecsia"));
        System.out.println("alexandra balance: " + account.getBalance("alexandra"));
        System.out.println("Active orders: " + orderBook.getActiveOrders().size());
    }
}
