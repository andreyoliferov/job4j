package products.storage;


import products.items.decorator.IFood;

import java.time.LocalDate;

/**
 * @autor aoliferov
 * @since 13.02.2019
 */
public class Trash extends Storage<IFood> {

    public Trash() {
        super((food) -> food.getExpaireDate().isBefore(LocalDate.now())
                || food.getExpaireDate().isEqual(LocalDate.now()));
    }
}
