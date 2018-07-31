package tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor Андрей
 * @since 30.07.2018
 */
public class Node<E extends Comparable<E>> {
    private final List<Node<E>> children = new ArrayList<>();
    private final E value;

    public Node(final E value) {
        this.value = value;
    }

    public void add(Node<E> child) {
        this.children.add(child);
    }

    public List<Node<E>> leaves() {
        return this.children;
    }

    public E getValue() {
        return value;
    }

    public boolean eqValue(E that) {
        return this.value.compareTo(that) == 0;
    }
}
