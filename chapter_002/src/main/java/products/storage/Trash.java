package products.storage;


import java.time.LocalDate;

/**
 * @autor aoliferov
 * @since 13.02.2019
 */
public class Trash extends Storage {

    public Trash() {
        super((food) -> food.getExpaireDate().isBefore(LocalDate.now())
                || food.getExpaireDate().isEqual(LocalDate.now()));
    }
}
