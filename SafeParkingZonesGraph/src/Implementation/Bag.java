package Implementation;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of Bag ADT
 * Implemented with the help of the Algorithms textbook and website
 * 
 * @author Orlando Ortega
 */
public class Bag<Item> implements Iterable<Item> {
    private Node<Item> first;    // beginning of bag
    private int size;               // number of elements in bag

    //node class representation
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    /**
	 * Bag constructor
	 * 
	 */
    public Bag() {
        first = null;
        size = 0;
    }

    /**
	 * Checks if the bag is empty
	 * 
	 * @return if the node is the first node is not null, then return false, otherwise return true
	 */
    public boolean isEmpty() {
        return first == null;
    }

    /**
	 * Gives the size of the bag
	 * 
	 * @return size of the bag
	 */
    public int size() {
        return size;
    }

    /**
	 * Adds and item to the bag
	 * 
	 * @param item is the item of the same type of the bag to be added
	 */
    public void add(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        size++;
    }


    /**
	 * Allows for iteration through the elements of the bag
	 * 
	 * @return Iterator for the bag
	 */
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
