package parser;

import java.time.LocalDateTime;

/**
 * https://dev.hh.ru/
 * @autor aoliferov
 * @since 12.11.2018
 */
public abstract class Hunter {

    protected StoreSQL store;
    protected LocalDateTime last;
    protected final String source;

    protected Hunter(StoreSQL store, String source) {
        this.store = store;
        this.source = source;
        this.last = store.getLastDate(source);
    }

    abstract void perform();
}
