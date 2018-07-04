package ru.job4j.bank;

/**
 * @autor Андрей
 * @since 05.07.2018
 */
public class User {

    public User(String name, String passport) {
        this.name = name;
        this.passport = passport;
    }

    private String name;
    private String passport;

    public String getPassport() {
        return this.passport;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return name.equals(user.name) && passport.equals(user.passport);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + passport.hashCode();
        return result;
    }
}
