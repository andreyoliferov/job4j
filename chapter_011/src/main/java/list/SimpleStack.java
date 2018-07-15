package list;

/**
 * @autor Андрей
 * @since 15.07.2018
 */
public class SimpleStack<E> {

    private MyLinkedList<E> data = new MyLinkedList<>();

    public E poll() {
        return data.deleteFirst();
    }

    public void push(E value) {
        data.add(value);
    }
}
