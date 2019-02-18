package products.items.decorator;

import java.time.LocalDate;

/**
 * @autor aoliferov
 * @since 18.02.2019
 */
public abstract class FoodDecorator implements IFood {

    private IFood food;

    protected FoodDecorator(IFood food) {
        this.food = food;
    }

    @Override
    public LocalDate getCreateDate() {
        return food.getCreateDate();
    }

    @Override
    public LocalDate getExpaireDate() {
        return food.getExpaireDate();
    }

    @Override
    public void setDisscount(float disscount) {
        food.setDisscount(disscount);
    }

}
