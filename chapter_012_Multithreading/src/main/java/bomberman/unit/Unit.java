package bomberman.unit;

/**
 * @autor Андрей
 * @since 31.05.2018
 */
public interface Unit {

    Position current();

    default String icon() {
        return String.format(
                "%s.png", this.getClass().getSimpleName()
        );

    }

    Unit copy(Position future);
}
