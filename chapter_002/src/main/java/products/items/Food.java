package products.items;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 * @autor aoliferov
 * @since 13.02.2019
 */
public abstract class Food {

    protected String name;
    protected LocalDate expaireDate;
    protected LocalDate createDate;
    protected double price;
    protected float disscount;

    public Food(String name, BigDecimal price, LocalDate createDate, LocalDate expaireDate) {
        this.name = name;
        this.price = price.doubleValue();
        this.createDate = createDate;
        this.expaireDate = expaireDate;
        this.disscount = 0f;
    }

    public void setDisscount(float disscount)  {
        assert  disscount > 0 && disscount < 1;
        this.disscount = disscount;
    }

    public BigDecimal getFullPrice() {
        return new BigDecimal(price).setScale(2, RoundingMode.CEILING);
    }

    public BigDecimal getDisscountPrice() {
        return new BigDecimal(price - price * disscount).setScale(2, RoundingMode.CEILING);
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public LocalDate getExpaireDate() {
        return expaireDate;
    }
}
