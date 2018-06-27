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
        if (tasks.size() != 0) {
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getPriority() > task.getPriority()) {
                    tasks.add(i, task);
                    break;
                }
            }
        } else {
            tasks.add(task);
        }
    }

    public Task take() {
        return this.tasks.poll();
    }
}
