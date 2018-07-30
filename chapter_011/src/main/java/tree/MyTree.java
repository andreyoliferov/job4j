package tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

/**
 * @autor Андрей
 * @since 30.07.2018
 */
public class MyTree<E extends Comparable<E>> implements SimpleTree<E> {

    public MyTree(E e) {
        this.root = new Node<>(e);
    }

    private Node<E> root;

    @Override
    public boolean add(E parent, E child) {
        boolean result = false;
        Optional<Node<E>> parentNode = this.findBy(parent);
        if (parentNode.isPresent()) {
            parentNode.get().add(new Node<>(child));
            result = true;
        }
        return result;
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (el.eqValue(value)) {
                rsl = Optional.of(el);
                break;
            }
            for (Node<E> child : el.leaves()) {
                data.offer(child);
            }
        }
        return rsl;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }
}
