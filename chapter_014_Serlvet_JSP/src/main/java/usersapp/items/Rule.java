package usersapp.items;

import java.util.Objects;
import java.util.UUID;

/**
 * Права для доступа
 * @autor aoliferov
 * @since 04.12.2018
 */
public class Rule {

    private String name;
    private UUID id;

    public Rule(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rule rule = (Rule) o;
        return Objects.equals(name, rule.name)
                && Objects.equals(id, rule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    @Override
    public String toString() {
        return "Rule{"
                + "name='" + name + '\''
                + ", id=" + id
                + '}';
    }
}
