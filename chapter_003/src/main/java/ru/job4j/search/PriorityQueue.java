package ru.job4j.search;

import java.util.LinkedList;

/**
 * @autor Андрей
 * @since 27.06.2018
 */
public class PriorityQueue {
    private LinkedList<Task> tasks = new LinkedList<>();

    /**
     * Метод вставляет в нужную позицию элемент.
     * Позиция определяется по полю приоритет.
     * @param task задача
     */
    public void put(Task task) {
        for (int i = 0; i <= tasks.size(); i++) {
            if(tasks.size() == i) {
                tasks.add(task);
                break;
            }
            if (tasks.get(i).getPriority() > task.getPriority()) {
                tasks.add(i, task);
                break;
            }
        }
    }

    public Task take() {
        return this.tasks.poll();
    }
}
