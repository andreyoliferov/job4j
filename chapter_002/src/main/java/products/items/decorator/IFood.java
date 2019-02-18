package products.items.decorator;

import java.time.LocalDate;

/**
 * @autor aoliferov
 * @since 18.02.2019
 */
public interface IFood {

    LocalDate getCreateDate();
    LocalDate getExpaireDate();
    void setDisscount(float disscount);
}
