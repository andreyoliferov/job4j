package products.storage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @autor aoliferov
 * @since 13.02.2019
 */
public class Shop extends Storage {

    public Shop() {
        super((food) -> {
            LocalDate create = food.getCreateDate();
            LocalDate expair = food.getExpaireDate();
            LocalDate now = LocalDate.now();
            float full = ChronoUnit.DAYS.between(create, expair);
            long ended = ChronoUnit.DAYS.between(create, now);
            float percent = ended / full;
            boolean result = false;
            if (percent >= 0.25 && percent < 0.75) {
                result = true;
            } else if (percent >= 0.75 && percent < 1) {
                result = true;
                food.setDisscount(0.30f);
            }
            return result;
        });
    }
}
