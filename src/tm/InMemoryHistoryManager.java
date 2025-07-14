package tm;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private Node<Task> head;
    private Node<Task> tail;

    private final Map<Integer,Node<Task>> nodeMap = new HashMap<>();

    private Node<Task> linkLast(Task task) {
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(oldTail, task, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        return newNode;
    }

    private List<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Node<Task> currentTask = head;
        while (currentTask != null) {
            tasks.add(currentTask.data);
            currentTask = currentTask.next;
        }
        return tasks.reversed();
    }

    private void removeNode(Node<Task> node) {
        if (node == head && node != tail) {
            head = node.next;
            node.next.prev = null;
        } else if (node == tail && node != head) {
            tail = node.prev;
            node.prev.next = null;
        } else if (node == tail) {
            head = null;
            tail = null;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        nodeMap.remove(node.data.getId());
    }

    @Override
    public void add(Task task) {
        if (nodeMap.containsKey(task.getId())) {
            remove(task.getId());
            nodeMap.put(task.getId(), linkLast(task));
        } else {
            nodeMap.put(task.getId(), linkLast(task));
        }
    }

    @Override
    public void remove(int id) {
        Node<Task> node = nodeMap.getOrDefault(id,null);
        if (node != null) {
            removeNode(node);
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    public Node<Task> getHead() {
        return head;
    }

    public Node<Task> getTail() {
        return tail;
    }

    public Map<Integer, Node<Task>> getNodeMap() {
        return nodeMap;
    }

    static class Node<E> {
        public E data;
        public Node<E> next;
        public Node<E> prev;

        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(data, node.data) && Objects.equals(next, node.next) && Objects.equals(prev, node.prev);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data, next, prev);
        }
    }
}


