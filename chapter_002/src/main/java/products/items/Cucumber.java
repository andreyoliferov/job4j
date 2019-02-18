package products.items;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @autor aoliferov
 * @since 18.02.2019
 */
public class Cucumber extends Vegetable {

    public Cucumber(String name, BigDecimal price, LocalDate createDate, LocalDate expaireDate,
                    StorageTemperature storageTemperature) {
        super(name, price, createDate, expaireDate, storageTemperature);
    }
}
