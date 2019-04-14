package Implementation;

/**
 * Implementation of LinearProbingHashST ADT
 * Implemented with the help of the Algorithms textbook and website
 * 
 * @author Orlando Ortega
 */
public class LinearProbingHashST<Key, Value> {

    private int n;           // number of key-value pairs in the symbol table
    private int m;           // size of linear probing table
    private Key[] keys;      // the keys
    private Value[] vals;    // the values

    /**
	 * Constructor for the hash Table
     *
     * @param  capacity the total number of spaces that should be pre-allocated
     */
    public LinearProbingHashST(int capacity) {
        m = capacity;
        n = 0;
        keys = (Key[])   new Object[m];
        vals = (Value[]) new Object[m];
    }

    /**
	 * Size of the hashTable
     *
     * @return size of the hashTable
     */
    public int size() {
        return n;
    }

    /**
	 * Checks if the hashTable is empty
     *
     * @return if size = 0 it is empty, else it is not empty
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
	 * Checks if the given key is within the hashTable
     *
     * @param key The key to be looked for within the hash table
     * @return if the given key is in the hashTable
     */
    public boolean contains(Key key) {
        return get(key) != null;
    }

    // hash function for keys - returns value between 0 and M-1
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    // resizes the hash table to the given capacity by re-hashing all of the keys
    private void resize(int capacity) {
        LinearProbingHashST<Key, Value> temp = new LinearProbingHashST<Key, Value>(capacity);
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        m    = temp.m;
    }

    /**
	 * Places a given key and it's value into the hash table
     *
     * @param key The key to be placed in the table
     * @param val The associated value of the given key
     */
    public void put(Key key, Value val) {

        if (val == null) {
            delete(key);
            return;
        }

        // double table size if 50% full
        if (n >= m/2) resize(2*m);

        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        keys[i] = key;
        vals[i] = val;
        n++;
    }

    /**
	 * Returns the value of the given key
     *
     * @param key the key used to find the value for
     * @return the value of the given key
     */
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
            if (keys[i].equals(key))
                return vals[i];
        return null;
    }

    /**
	 * Deletes a key from the hash table
     *
     * @param key the key to be deleted
     */
    public void delete(Key key) {
        if (!contains(key)) return;

        // find position i of key
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % m;
        }

        // delete key and associated value
        keys[i] = null;
        vals[i] = null;

        // rehash all keys in same cluster
        i = (i + 1) % m;
        while (keys[i] != null) {
            // delete keys[i] an vals[i] and reinsert
            Key   keyToRehash = keys[i];
            Value valToRehash = vals[i];
            keys[i] = null;
            vals[i] = null;
            n--;
            put(keyToRehash, valToRehash);
            i = (i + 1) % m;
        }

        n--;

        // halves size of array if it's 12.5% full or less
        if (n > 0 && n <= m/8) resize(m/2);

    }

    //allows for iteration through the keys in the table
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < m; i++)
            if (keys[i] != null) queue.enqueue(keys[i]);
        return queue;
    }

}
