package service;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final CustomLinkedList history = new CustomLinkedList();

    @Override
    public void addTaskToHistory(Task task) {
        history.linklast(task);

    }

    @Override
    public void remove(int id) {
        history.removeNode(id);
    }

    @Override
    public List<Task> gethistory() {
        return history.getTasks();
    }

    private static class CustomLinkedList {
        public static class Node<T> {
            public T task;
            public Node<T> next;
            public Node<T> prev;

            public Node(T task, Node<T> next, Node<T> prev) {
                this.task = task;
                this.next = next;
                this.prev = prev;
            }
        }

        private Node<Task> first;
        private Node<Task> last;

        private final Map<Integer, CustomLinkedList.Node<Task>> nodeMap = new HashMap<>();

        public void linklast(Task task) {
            if (task == null) {
                return;
            }

            removeNode(nodeMap.get(task.getTaskId()));

            final Node<Task> oldLast = last;
            final Node<Task> newNode = new Node<>(task, oldLast, null);
            last = newNode;
            if (oldLast == null) {
                first = newNode;
            } else {
                oldLast.next = newNode;
            }
            nodeMap.put(task.getTaskId(), newNode);
        }

        private void removeNode(int id) {
            removeNode(nodeMap.remove(id));

        }

        private void removeNode(Node<Task> node) {
            if (node == null) {
                return;
            }
            final Node<Task> prev = node.prev;
            final Node<Task> next = node.next;

            if (prev == null) {
                first = next;
            } else {
                prev.next = next;
            }
            if (next == null) {
                last = prev;
            } else {
                next.prev = prev;
            }
            node.task = null;

        }

        private List<Task> getTasks() {
            List<Task> tasks = new ArrayList<>();
            for (Node<Task> node = first; node != null; node = node.next) {
                tasks.add(node.task);
                break;
            }
            return tasks;
        }


    }


}


