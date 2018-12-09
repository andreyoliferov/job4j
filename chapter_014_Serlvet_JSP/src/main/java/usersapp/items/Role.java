package usersapp.items;

import java.util.List;
import java.util.UUID;

/**
 * Роли пользователя
 * @autor aoliferov
 * @since 03.12.2018
 */
public class Role {

    private String name;
    private UUID id;
    /**
     * Права для доступа
     */
    private List<Rule> rules;

    public Role(UUID id, String name, List<Rule> rules) {
        this.name = name;
        this.id = id;
        this.rules = rules;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public List<Rule> getRules() {
        return rules;
    }
}
