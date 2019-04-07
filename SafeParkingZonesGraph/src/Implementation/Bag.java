package Implementation;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Bag<Item> implements Iterable<Item> {
    private Node<Item> first;    // beginning of bag
    private int size;               // number of elements in bag

    //node class representation
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    //constructor for bag
    public Bag() {
        first = null;
        size = 0;
    }

    //returns if the bag is empty
    public boolean isEmpty() {
        return first == null;
    }

    //returns the size of the bag
    public int size() {
        return size;
    }

    //appends an item to the bag
    public void add(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        size++;
    }


    //allows for iteration through the items of the bag
    public Iterator<Item> iterator()  {
        return new ListIterator<Item>(first);  
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
}
