package list;


/**
 * @autor Андрей
 * @since 15.07.2018
 */
public class SimpleQueue<E> {

    private MyLinkedList<E> data = new MyLinkedList<>();

    public E poll() {
        return data.deleteLast();
    }

    public void push(E value) {
        data.add(value);
    }
}
