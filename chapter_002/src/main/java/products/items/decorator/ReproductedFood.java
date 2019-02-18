package products.items.decorator;


/**
 * @autor aoliferov
 * @since 18.02.2019
 */
public class ReproductedFood extends FoodDecorator {

    private boolean canReproduct;

    protected ReproductedFood(IFood food, boolean canReproduct) {
        super(food);
        this.canReproduct = canReproduct;
    }

    public boolean isCanReproduct() {
        return canReproduct;
    }
}
