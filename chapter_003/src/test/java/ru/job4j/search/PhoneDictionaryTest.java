package ru.job4j.search;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @autor Андрей
 * @since 27.06.2018
 */
public class PhoneDictionaryTest {

    @DataProvider
    public Object[][] data() {
        return new Object[][]{
                {"Petr"},
                {"Arsent"},
                {"4872"},
                {"ansk"}
        };
    }
    @Test(dataProvider = "data")
    public void whenFindValid(String find) {
        var phones = new PhoneDictionary();
        phones.add(new Person("Petr", "Arsentev", "534872", "Bryansk"));
        phones.add(new Person("PetrPetr", "ArsentevArsentev", "664872", "Saransk"));
        phones.add(new Person("Andrey", "Oliferov", "234888", "Omsk"));
        List<Person> persons = phones.find(find);
        Iterator<Person> iterator = persons.iterator();
        assertThat(iterator.next().getSurname(), is("Arsentev"));
        assertThat(iterator.next().getSurname(), is("ArsentevArsentev"));
        assertThat(persons.size(), is(2));
    }

    @Test(dataProvider = "data")
    public void whenInvalidFind(String find) {
        var phones = new PhoneDictionary();
        phones.add(new Person("Maksim", "Orlov", "276752", "Novosibirsk"));
        phones.add(new Person("Andrey", "Oliferov", "234888", "Omsk"));
        List<Person> persons = phones.find(find);
        assertThat(persons.size(), is(0));
    }
}
