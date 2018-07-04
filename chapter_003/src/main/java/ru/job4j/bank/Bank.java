package ru.job4j.bank;

import java.util.*;

/**
 * Базовый класс задания Bank
 * Банковские переводы. [#10038]
 * @autor Андрей
 * @since 05.07.2018
 */
public class Bank {

    /**
     * основное поле с данными
     * Map сортируется по имени пользователя
     * у каждого пользователя набор уникальных аккаунтов, уникальность аккаунта определяется по реквизитам
     */
    private TreeMap<User, LinkedHashSet<Account>> data = new TreeMap<>(Comparator.comparing(User::getName));

    /**
     * добавить нового пользователя
     * @param user
     */
    public void addUser(User user) {
        data.putIfAbsent(user, new LinkedHashSet<>());
    }

    /**
     * удалить пользователя
     * @param user пользователь
     */
    public void deleteUser(User user) {
        data.remove(user);
    }

    /**
     * добавить аккаунт пользователю по паспорту
     * @param passport паспорт
     * @param account добавляемый аккаунт
     */
    public void addAccountToUser(String passport, Account account) {
        data.get(findUser(passport)).add(account);
    }

    /**
     * удалить аккаунт у пользователя по паспорту
     * @param passport паспорт
     * @param account удаляемый аккаунт
     */
    public void deleteAccountFromUser(String passport, Account account) {
        data.get(findUser(passport)).remove(account);
    }

    /**
     * Метод возвращает все аккаунты по паспорту пользователя
     * @param passport паспорт
     * @return лист аккаунтов
     */
    public List<Account> getUserAccounts(String passport) {
        return new ArrayList<>(data.get(findUser(passport)));
    }

    /**
     * поиск пользоваетля по паспортным данным
     * @param passport паспорт
     * @return найденный пользователь
     */
    private User findUser(String passport) {
        Set<User> users = data.keySet();
        User finded = null;
        for (User user : users) {
            if (user.getPassport().equals(passport)) {
                finded = user;
            }
        }
        return finded;
    }

    /**
     * Метод выполняет поиск аккаунта по реквизитам и пользователю
     * @param user пользоваетль
     * @param requisite реквизиты аккаунта
     * @return найденный аккаунт
     */
    private Account findAccount(User user, String requisite) {
        LinkedHashSet<Account> all = data.get(user);
        Account finded = null;
        for (Account account : all) {
            if (account.getRequisites().equals(requisite)) {
                finded = account;
            }
        }
        return finded;
    }

    /**
     * метод перемещает средства с одного аккаунта на другой
     * @param srcPassport паспорт первого пользователя
     * @param srcRequisite реквизиты аккаунта первого пользователя
     * @param destPassport павспорт второго пользователя
     * @param destRequisite реквизиты аккаунта второго пользователя
     * @param amount сумма перевода от первого второму пользователю
     * @return булево - успешный перевод
     */
    public boolean transferMoney(String srcPassport, String srcRequisite, String destPassport,
                                  String destRequisite, double amount) {
        boolean transfer = false;
        if (amount > 0) {
            User srcUser = this.findUser(srcPassport);
            User destUser = this.findUser(destPassport);
            if (srcUser != null && destUser != null) {
                Account srcAccount = this.findAccount(srcUser, srcRequisite);
                Account destAccount = this.findAccount(destUser, destRequisite);
                if (srcAccount != null && destAccount != null) {
                    if (srcAccount.getValue() >= amount) {
                        srcAccount.setValue(srcAccount.getValue() - amount);
                        destAccount.setValue(destAccount.getValue() + amount);
                        transfer = true;
                    }
                }
            }
        }
        return transfer;
    }

}
