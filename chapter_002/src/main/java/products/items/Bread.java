package products.items;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @autor aoliferov
 * @since 13.02.2019
 */
public class Bread extends Food {
    public Bread(String name, BigDecimal price, LocalDate createDate, LocalDate expaireDate) {
        super(name, price, createDate, expaireDate);
    }
}
