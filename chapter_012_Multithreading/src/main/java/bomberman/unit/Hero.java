package bomberman.unit;

/**
 * @autor aoliferov
 * @since 24.09.2018
 */
public class Hero implements Unit{

    private Position current;

    @Override
    public Position current() {
        return this.current;
    }

    @Override
    public Unit copy(Position future) {
        return null;
    }
}
