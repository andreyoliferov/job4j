package synchro;

import java.util.Objects;
import java.util.UUID;

/**
 * @autor Андрей
 * @since 11.08.2018
 */
public class User {

    private UUID id;

    private int amount;

    public User(int amount) {
        this.id = UUID.randomUUID();
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return amount == user.amount
                && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount);
    }
}
