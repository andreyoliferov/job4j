package ru.job4j.converter;

/**
 * Конвертор валют
 * @author Андрей Олиферов
 * @since  02.05.2018.
 */
public class Converter {

    /**
     * Курс евро в рублях.
     */
    private int euroToRubleRate = 70;

    /**
     * курс доллара в рублях.
     */
    private int dollarToRubleRate = 60;

    /**
     * Конвертируем рубли в евро.
     * @param value рубли.
     * @return Евро.
     */
    public int rubleToEuro(int value) {
        return value / this.euroToRubleRate;
    }

    /**
     * Конвертируем рубли в доллары.
     * @param value рубли.
     * @return Доллоры
     */
    public int rubleToDollar(int value) {
        return value / this.dollarToRubleRate;
    }

    /**
     * Конвертируем евро в рубли.
     * @param value рубли.
     * @return Евро.
     */
    public int euroToRuble(int value) {
        return value * this.euroToRubleRate;
    }

    /**
     * Конвертируем доллары в рубли.
     * @param value рубли.
     * @return Доллоры
     */
    public int dollarToRuble(int value) {
        return value * this.dollarToRubleRate;
    }
}
