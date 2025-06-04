package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private Node<Task> head;
    private Node<Task> tail;

    private final Map<Integer,Node<Task>> nodeMap = new HashMap<>();

    private Node<Task> linkLast (Task task) {
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(oldTail, task , null);
        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else
            oldTail.next = newNode;
        return newNode;
    }

    private ArrayList<Task> getTasks(){
        ArrayList<Task> tasks = new ArrayList<>();
        Node<Task> currentTask = head;
        while (currentTask != null) {
            tasks.add(currentTask.data);
            currentTask = currentTask.next;
        }
        return tasks;
    }

    private void removeNode(Node<Task> node) {
        if (node == head) {
            head = node.next;
            node.next.prev = null;
        } else if (node == tail){
            tail = node.prev;
            node.prev.next = null;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        nodeMap.remove(node.data.getId());
    }

    @Override
    public void add(Task task) {
        if (task instanceof Epic) {
            if (nodeMap.containsKey(task.getId())) {
                remove(task.getId());
                nodeMap.put(task.getId(), linkLast(task));
            } else {
                nodeMap.put(task.getId(), linkLast(task));
            }
        } else if (task instanceof Subtask) {
            if (nodeMap.containsKey(task.getId())) {
                remove(task.getId());
                nodeMap.put(task.getId(), linkLast(task));
            } else {
                nodeMap.put(task.getId(), linkLast(task));
            }
        } else {
            if (nodeMap.containsKey(task.getId())) {
                remove(task.getId());
                nodeMap.put(task.getId(), linkLast(task));
            } else {
                nodeMap.put(task.getId(), linkLast(task));
            }
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
    public ArrayList<Task> getHistory() {
        return getTasks();
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
    }
}


