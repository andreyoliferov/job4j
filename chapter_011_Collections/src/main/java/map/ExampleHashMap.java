package map;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @autor Андрей
 * @since 29.07.2018
 */
public class ExampleHashMap<K, V>  implements Iterable<ExampleHashMap.Node> {

    private Node[] table;
    private double fillFactor = 0.75;
    private int length = 16;
    private int quantity = 0;

    public ExampleHashMap() {
        this.table = new Node[length];
    }

    public ExampleHashMap(int length) {
        this.length = length;
        this.table = new Node[length];
    }

    boolean insert(K key, V value) {
        boolean result = false;
        Node node = this.getNode(key);
        if (node == null) {
            table[this.indexFor(key.hashCode(), this.length)] = new Node<>(key, value);
            result = true;
            quantity++;
        }

        double d = (double) quantity / length;
        if (d >= fillFactor) {
            this.resize();
        }
        return result;
    }

    public V get(K key) {
        V result = null;
        Node node = this.getNode(key);
        if (node != null) {
            result = (V) node.getValue();
        }
        return result;
    }

    public boolean delete(K key) {
        boolean result = false;
        Node node = this.getNode(key);
        if (node != null && node.getKey().equals(key)) {
            table[this.indexFor(key.hashCode(), this.length)] = null;
            result = true;
        }
        return result;
    }

    private int indexFor(int hash, int length) {
        return hash % (length - 1);
    }


    private void resize() {
        Node[] temp = this.table;
        this.length *= 2;
        this.table = new Node[this.length];
        this.quantity = 0;
        for (Node node : temp) {
            if (node != null) {
                this.insert((K) node.getKey(), (V) node.getValue());
            }
        }
    }

    private Node getNode(K key) {
        int index = indexFor(key.hashCode(), this.length);
        return table[index];
    }

    @Override
    public Iterator<Node> iterator() {
        return new Iterator<>() {

            int index = 0;
            int position = 0;

            @Override
            public boolean hasNext() {
                return index < quantity;
            }

            @Override
            public Node next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                while (table[position] == null) {
                    position++;
                }
                Node finded = table[position];
                position++;
                index++;
                return finded;
            }
        };
    }

    public static class Node<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            V temp = this.value;
            this.value = value;
            return temp;
        }
    }
}

