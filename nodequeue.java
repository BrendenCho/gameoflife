package gameoflife;

import java.util.LinkedList;

public class nodequeue implements queueinterface {

    LinkedList<node> list = new LinkedList<node>();

    @Override
    public int numItems() {
        return list.size();
    }

    @Override
    public node peek() {
        if (list.isEmpty() == true) {
            throw new queuexception("Queue empty");
        } else {
            return list.getFirst();
        }
    }

    @Override
    public node dequeue() {
        if (list.isEmpty() == true) {
            throw new queuexception("Queue empty");
        } else {
            node temp = list.getFirst();
            list.remove(0);
            return temp;
        }
    }

    @Override
    public void enqueue(node n) {
        list.add(n);
    }

    @Override
    public void dequeueAll() {
        list.clear();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

}