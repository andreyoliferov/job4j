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
        Node slow = first;
        Node fast = first;
        while (fast != null && fast.next != null && !result) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                result = true;
            }
        }
        return result;
    }
}
