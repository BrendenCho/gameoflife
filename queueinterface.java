package gameoflife;

public interface queueinterface {
 
public int numItems();    
public node peek();
public node dequeue();
public void enqueue(node n);
public void dequeueAll();
public boolean isEmpty();
}