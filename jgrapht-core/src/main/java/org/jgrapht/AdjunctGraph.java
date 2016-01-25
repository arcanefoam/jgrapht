/* ==========================================
 * JGraphT : a free Java graph-theory library
 * ==========================================
 *
 * Project Info:  http://jgrapht.sourceforge.net/
 * Project Creator:  Barak Naveh (http://sourceforge.net/users/barak_naveh)
 *
 * (C) Copyright 2003-2015, by Barak Naveh and Contributors.
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
/* ------------------
 * DirectedGraph.java
 * ------------------
 * (C) Copyright 2003-2015, by Barak Naveh and Contributors.
 *
 * Original Author:  Barak Naveh
 * Contributor(s):   Horacio Hoyos
 *
 * $Id$
 *
 * Changes
 * -------
 * 28-Nov-2015 : Initial revision (HH);
 * 05-Jan-2016 : Added method for adjunct access (HH);
 * 
 */
package org.jgrapht;

import java.util.Set;

import org.jgrapht.graph.UnmodifiableGraph;

/**
 * A graph that extends another graph, but is not essential to it (i.e. it does
 * not change the structure of the extended graph). The adjunct graph provides a
 * joint view of the base graph and itself as a single graph. However, the base
 * graph is an {@link UnmodifiableGraph} and hence, only the adjunct part of the
 * graph can be modified.  
 * 
 *
 * @param <V> the value type
 * @param <E> the element type
 */
public interface AdjunctGraph<V, E> extends Graph<V, E> {
	
	/**
	 * Returns the graph that is the base for this graph. The source graph is 
	 * wrapped in an UnmodifiableGraph to guarantee that it can not be modified
	 * while accessing the adjunct graph.
	 *  
	 * @return The base graph
	 */
	public Graph<V, E> getPrimaryGraph();
	////{@link #getComponentAt(int, int) getComponentAt} method.
	/**
     * Returns <tt>true</tt> if and only if the adjunct portion of the graph
     * contains an edge going from the source vertex to the target vertex.
     * 
     * @see #containsEdge(Object, Object)
     * @param sourceVertex source vertex of the edge.
     * @param targetVertex target vertex of the edge.
     *
     * @return <tt>true</tt> if this graph contains the specified edge.
     */
    public boolean adjunctContainsEdge(V sourceVertex, V targetVertex);

    /**
     * Returns <tt>true</tt> if the adjunct portion of the graph contains the
     * specified edge.
     * 
     * @see #containsEdge(Object)
     * @param e edge whose presence in this graph is to be tested.
     *
     * @return <tt>true</tt> if this graph contains the specified edge.
     */
    public boolean adjunctContainsEdge(E e);

    /**
     * Returns <tt>true</tt> if the adjunct portion of the graph contains the
     * specified vertex.
     * 
     * @see #containsVertex(Object)
     * @param v vertex whose presence in this graph is to be tested.
     *
     * @return <tt>true</tt> if this graph contains the specified vertex.
     */
    public boolean adjunctContainsVertex(V v);
    
    /**
     * Returns a set of the edges contained in the adjunct portion of the graph.
     * 
     * @see #edgeSet()
     * 
     * @return a set of the edges contained in this graph.
     */
    public Set<E> adjunctEdgeSet();

    /**
     * Returns a set of all edges touching the specified vertex that belong to
     * the adjunct portion of the graph
     *
     * @see #edgesOf(Object)
     * @param vertex the vertex for which a set of touching edges is to be
     * returned.
     *
     * @return a set of all edges touching the specified vertex.
     *
     * @throws IllegalArgumentException if vertex is not found in the graph.
     * @throws NullPointerException if vertex is <code>null</code>.
     */
    public Set<E> adjunctEdgesOf(V vertex);
    
    /**
     * Returns a set of the vertices contained in the adjunct portion of the
     * graph.
     * 
     * @see #vertexSet()
     *  
     * @return a set view of the vertices contained in this graph.
     */
    public Set<V> adjunctVertexSet();
	
	

}
