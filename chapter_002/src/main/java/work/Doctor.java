package work;

/**
 * @autor Андрей
 * @since 16.05.2018
 */
public class Doctor extends Profession {

    public Diagnose heal(Pacient pacient) {
        return new Diagnose();
    }
}
