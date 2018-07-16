package list;

import org.testng.annotations.Test;

import static list.HasCycle.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @autor Андрей
 * @since 16.07.2018
 */
public class HasCycleTest {

    private HasCycle cicle = new HasCycle();

    @Test
    public void whenCycleInCenter() {
        Node<Integer> first = new Node<>(1);
        Node<Integer> two = new Node<>(2);
        Node<Integer> third = new Node<>(3);
        Node<Integer> four = new Node<>(4);
        first.next = two;
        two.next = third;
        third.next = four;
        four.next = two;

        assertThat(cicle.hasCycle(first), is(true));
    }

    @Test
    public void whenCycleInLast() {
        Node<Integer> first = new Node<>(1);
        Node<Integer> two = new Node<>(2);
        Node<Integer> third = new Node<>(3);
        first.next = two;
        two.next = third;
        third.next = first;

        assertThat(cicle.hasCycle(first), is(true));
    }

    @Test
    public void whenNoCycle() {
        Node<Integer> first = new Node<>(1);
        Node<Integer> two = new Node<>(2);
        Node<Integer> third = new Node<>(3);
        first.next = two;
        two.next = third;

        assertThat(cicle.hasCycle(first), is(false));
    }
}
