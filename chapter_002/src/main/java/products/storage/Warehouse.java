package products.storage;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @autor aoliferov
 * @since 13.02.2019
 */
public class Warehouse extends Storage {

    public Warehouse() {
        super((food) -> {
            LocalDate create = food.getCreateDate();
            LocalDate expair = food.getExpaireDate();
            LocalDate now = LocalDate.now();
            float full = ChronoUnit.DAYS.between(create, expair);
            long ended = ChronoUnit.DAYS.between(create, now);
            float percent = ended / full;
            boolean result = false;
            if (percent < 0.25) {
                result = true;
            }
            return result;
        });
    }
}
