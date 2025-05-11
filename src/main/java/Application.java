import Event.OrderPlaced;
import Management.CommandHandler;
import Management.EventStore;
import Management.SystemReplayer;
import Model.Account;
import Model.OrderBook;

import java.util.Optional;

public class Application {
    public static void main(String[] args) {
        EventStore store = new EventStore();
        OrderBook orderBook = new OrderBook();
        Account account = new Account();
        CommandHandler handler = new CommandHandler(store, orderBook, account);
        SystemReplayer systemReplayer = new SystemReplayer(store);

        handler.depositFunds("alecsia", 1000);
        handler.depositFunds("alexandra", 500);

        handler.placeOrder("alecsia", false, 10, 50);
        handler.placeOrder("alexandra", true, 10, 50);

        handler.placeOrder("alexandra", true, 5, 60);
        Optional<String> maybeOrderId = orderBook.getActiveOrders().values().stream()
                .filter(o -> o.getUserId().equals("alexandra") && o.getPrice() == 60)
                .map(OrderPlaced::getOrderId)
                .findFirst();

        maybeOrderId.ifPresent(id -> handler.cancelOrder(id, "alexandra"));


        systemReplayer.replayAll(account, orderBook);

        System.out.println("===== Event Log =====");
        store.getAllEvents().forEach(System.out::println);

        System.out.println("alecsia balance: " + account.getBalance("alecsia"));
        System.out.println("alexandra balance: " + account.getBalance("alexandra"));
        System.out.println("Active orders: " + orderBook.getActiveOrders().size());
        System.out.println("Cancelled orders: " + orderBook.getCancelledOrders().size());
    }
}
