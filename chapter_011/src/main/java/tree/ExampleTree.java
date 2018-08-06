package tree;

import java.util.*;

/**
 * @autor Андрей
 * @since 30.07.2018
 */
public class ExampleTree<E extends Comparable<E>> implements SimpleTree<E> {

    private int size;
    private Node<E> root;

    public ExampleTree(E e) {
        this.root = new Node<>(e);
        this.size = 1;
    }

    @Override
    public boolean add(E parent, E child) {
        boolean result = false;
        Optional<Node<E>> parentNode = this.findBy(parent);
        if (parentNode.isPresent()) {
            parentNode.get().add(new Node<>(child));
            result = true;
            size++;

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

    public boolean isBinary() {
        Queue<Node<E>> queue = new LinkedList<>();
        queue.add(root);
        boolean result = true;
        while (!queue.isEmpty() && result) {
            Node<E> elem = queue.poll();
            List<Node<E>> children =  elem.leaves();
            for (Node<E> child : children) {
                queue.add(child);
            }
            if (children.size() > 2) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            {
                queue = new LinkedList<>();
            }

            private int index = 0;
            Queue<Node<E>> queue;

            @Override
            public boolean hasNext() {
                return size > index;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (index == 0) {
                    queue.add(root);
                }
                Node<E> elem = queue.poll();
                for (Node<E> child : elem.leaves()) {
                    queue.offer(child);
                }
                index++;
                return elem.getValue();
            }
        };
    }
}
