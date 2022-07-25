package inmemory.manager;

import inmemory.Node;
import inmemory.interfaces.HistoryManager;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> historyOfRequestsList;
    private final Map<Integer, Node<Task>> tasksIndexInHistoryList;
    private Node<Task> head;
    private Node<Task> tail;
    private InMemoryHistoryManager mapNode;

    public InMemoryHistoryManager() {
        this.historyOfRequestsList = new ArrayList<>();
        this.tasksIndexInHistoryList = new HashMap<>();
        this.head = null;
        this.tail = null;
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            if (tasksIndexInHistoryList.containsKey(task.getId())) {
                remove(task.getId());
            }
            tasksIndexInHistoryList.put(task.getId(), linkLast(task));
        }
    }

    @Override
    public void remove(int id) {
        removeNode(tasksIndexInHistoryList.get(id));
        tasksIndexInHistoryList.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void clear() {
        Node head = new Node(null, null, null);
        Node tail = head;
        mapNode.clear();
    }

    private Node<Task> linkLast(Task task) {
        Node<Task> node;
        if (head == null) {
            node = new Node<>(task, null, null);
            head = node;
        } else {
            node = new Node<>(task, tail, null);
            tail.setNext(node);
        }
        tail = node;
        return node;
    }

    private List<Task> getTasks() {
        historyOfRequestsList.clear();
        if (head != null) {
            Node<Task> currentNode = head;
            while (currentNode.getNext() != null) {
                historyOfRequestsList.add(currentNode.getTask());
                currentNode = currentNode.getNext();
            }
            historyOfRequestsList.add(tail.getTask());
        }
        return historyOfRequestsList;
    }

    private void removeNode(Node<Task> node) {
        if (node != null) {
            Node<Task> prevNode = node.getPrev();
            Node<Task> nextNode = node.getNext();
            if (node == head && nextNode != null) {
                nextNode.setPrev(null);
                head = nextNode;
                return;
            }
            if (node == tail && prevNode != null) {
                prevNode.setNext(null);
                tail = prevNode;
                node = null;
                return;
            }
            if (node == head && node == tail) {
                head = null;
                tail = null;
                node = null;
                return;
            }
            if (node != tail && node != head) {
                prevNode.setNext(nextNode);
                nextNode.setPrev(prevNode);
                return;
            }
        }
    }
}