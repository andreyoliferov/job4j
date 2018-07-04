package ru.job4j.bank;

/**
 * @autor Андрей
 * @since 05.07.2018
 */
public class Account {

    public Account(String requisites, double value) {
        this.requisites = requisites;
        this.value = value;
    }

    private double value;
    private String requisites;

    public double getValue() {
        return value;
    }

    public String getRequisites() {
        return requisites;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return requisites.equals(account.requisites);
    }

    @Override
    public int hashCode() {
        return requisites.hashCode();
    }
}
