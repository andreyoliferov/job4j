package generic;

import java.util.Objects;

/**
 * @autor Андрей
 * @since 10.07.2018
 */
public class User extends Base implements Comparable<User> {

    public User(String id, String name) {
        super(id);
        this.name = name;
    }

    public User(String id) {
        super(id);
    }

    private String name;

    @Override
    public int compareTo(User o) {
        int result = this.getId().compareTo(o.getId());
        if (result == 0) {
            result = this.name.compareTo(o.name);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
