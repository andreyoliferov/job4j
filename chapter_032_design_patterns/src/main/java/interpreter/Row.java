package interpreter;

/**
 * @autor aoliferov
 * @since 17.08.2019
 */
public class Row {

    private String name;
    private String surname;

    Row(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }

}
