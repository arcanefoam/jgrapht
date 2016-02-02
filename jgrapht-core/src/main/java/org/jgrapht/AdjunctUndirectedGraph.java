package org.jgrapht;

public interface AdjunctUndirectedGraph<V, E> extends AdjunctGraph<V, E> {

    /**
     * Returns the degree of the specified vertex in the adjunct graph. A degree of a vertex in an
     * undirected graph is the number of edges touching that vertex.
     *
     * @param vertex vertex whose degree is to be calculated.
     *
     * @return the degree of the specified vertex.
     */
    public int adjunctDegreeOf(V vertex);

}
