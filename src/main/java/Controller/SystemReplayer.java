package Controller;

import Event.BaseEvent;
import Model.Account;
import Model.OrderBook;

public class SystemReplayer {
    private final EventStore store;

    public SystemReplayer(EventStore store) {
        this.store = store;
    }

    public void replayAll(Account account, OrderBook book) {
        for (BaseEvent e : store.getAllEvents()) {
            account.apply(e);
            book.apply(e);
        }
    }
}
