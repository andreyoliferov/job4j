package list;

/**
 * @autor Андрей
 * @since 16.07.2018
 */
public class HasCycle {

    public static class Node<T> {
        public Node(T value) {
            this.value = value;
        }

        T value;
        Node<T> next;
    }

    /**
     * Метод возвращает истину если связный список зациклен
     * @param first первая нода в связном списке
     * @return булево
     */
    boolean hasCycle(Node first) {
        boolean result = false;
        Node temp = first;
        int out = 0;
        while (!(temp.next == null || result)) {
            temp = temp.next;
            Node tempIn = first;
            for (int i = 0; i <= out; i++) {
                if (tempIn.equals(temp.next)) {
                    result = true;
                    break;
                }
                tempIn = tempIn.next;
            }
            out++;
        }
        return result;
    }
}
