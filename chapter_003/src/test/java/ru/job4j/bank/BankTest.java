package ru.job4j.bank;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.testng.Assert.assertTrue;

/**
 * Тестирование класса Bank
 * @autor Андрей
 * @since 08.07.2018
 */
public class BankTest {

    private Bank bank;
    private User andrey;
    private User katay;
    private Account forAndrey;
    private Account forKatya;


    @BeforeMethod
    public void testStart() {
        bank = new Bank();
        andrey = new User("Andrey", "112233");
        katay = new User("Katay", "445566");
        forAndrey = new Account("123456", 1000.7);
        forKatya = new Account("654321", 50.9);
        bank.addUser(andrey);
        bank.addUser(katay);
        bank.addAccountToUser("112233", forAndrey);
        bank.addAccountToUser("445566", forKatya);
    }

    @Test
    public void whenSuccessfulTransfer() {
        boolean tranfer = bank.transferMoney("112233", "123456",
                "445566", "654321", 600.2);
        assertThat(tranfer, is(true));
        assertThat(forAndrey.getValue(), is(400.5));
        assertThat(forKatya.getValue(), is(651.1));
    }

    @Test
    public void whenUnsuccessfulTransfer() {
        boolean tranfer = bank.transferMoney("112233", "123456",
                "445566", "654321", -600.2);
        assertThat(tranfer, is(false));
        assertThat(forAndrey.getValue(), is(1000.7));
        assertThat(forKatya.getValue(), is(50.9));
    }

    @Test
    public void whenUnsuccessfulTransferMinusAmount() {
        boolean tranfer = bank.transferMoney("112233", "123456",
                "445566", "654321", 1600.2);
        assertThat(tranfer, is(false));
        assertThat(forAndrey.getValue(), is(1000.7));
        assertThat(forKatya.getValue(), is(50.9));
    }

    @Test
    public void whenUnsuccessfulTransferNotHaveAccount() {
        boolean tranfer = bank.transferMoney("112233", "123456",
                "445566", "654341", 600.2);
        assertThat(tranfer, is(false));
        assertThat(forAndrey.getValue(), is(1000.7));
        assertThat(forKatya.getValue(), is(50.9));
    }

    @Test
    public void whenUnsuccessfulTransferNotHaveUser() {
        boolean tranfer = bank.transferMoney("112233", "123456",
                "447766", "654321", 600.2);
        assertThat(tranfer, is(false));
        assertThat(forAndrey.getValue(), is(1000.7));
        assertThat(forKatya.getValue(), is(50.9));
    }

    @Test
    public void whenDeleteUser() {
        bank.deleteUser(andrey);
        boolean tranfer = bank.transferMoney("112233", "123456",
                "445566", "654321", 600.2);
        assertThat(tranfer, is(false));
        assertThat(forAndrey.getValue(), is(1000.7));
        assertThat(forKatya.getValue(), is(50.9));
    }

    @Test
    public void whenDeleteAccount() {
        bank.deleteAccountFromUser("112233", forAndrey);
        assertTrue(bank.getUserAccounts("112233").size() == 0);
        boolean tranfer = bank.transferMoney("112233", "123456",
                "445566", "654321", 600.2);
        assertThat(tranfer, is(false));
        assertThat(forAndrey.getValue(), is(1000.7));
        assertThat(forKatya.getValue(), is(50.9));
    }

    @Test
    public void whenAddNotUniqAccountWhenNotAdd() {
        bank.addAccountToUser("445566", new Account("654321", 200));
        bank.addAccountToUser("445566", new Account("654321", 150));
        assertTrue(bank.getUserAccounts("445566").size() == 1);
        assertTrue(forKatya.getValue() == 50.9);
    }
}
