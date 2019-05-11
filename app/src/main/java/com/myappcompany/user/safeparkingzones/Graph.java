package com.myappcompany.user.safeparkingzones;

/**
 * Implementation of Graph ADT
 * Implemented with the help of the Algorithms textbook and website
 *
 * @author Orlando Ortega
 */
public class Graph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    /**
     * Constructor for the graph
     *
     * @param  V number of vertices
     */
    public Graph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this graph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph
     */
    public int E() {
        return E;
    }

    /**
     * Adds the undirected edge between v and w to this graph.
     *
     * @param  v origin vertex
     * @param  w destination vertex
     */
    public void addEdge(int v, int w) {
        E++;
        adj[v].add(w);
        adj[w].add(v);
    }


    /**
     * Returns the vertices adjacent to the given vertex.
     *
     * @param  v the vertex
     * @return the Iterable list of adjacent vertices
     */
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }


    /**
     * Returns a string representation of this graph.
     *
     * @return the string representation of the graph
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

}