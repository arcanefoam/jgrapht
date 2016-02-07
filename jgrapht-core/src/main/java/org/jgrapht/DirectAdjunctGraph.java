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
 * 25-Jan-2016 : Initial revision (HH);
 *
 */
package org.jgrapht;

import java.util.Set;

/**
 * A Direct Adjunct Graph provides the {@link DirectedGraph} methods to access only members of the adjunct graph.
 *
 * @param <V> the value type
 * @param <E> the element type
 */
public interface DirectAdjunctGraph<V, E> extends DirectedGraph<V, E> {

    /**
     * Returns the "in degree" of the specified vertex. An in degree of a vertex
     * in a directed graph is the number of inward directed edges from that
     * vertex. See <a href="http://mathworld.wolfram.com/Indegree.html">
     * http://mathworld.wolfram.com/Indegree.html</a>.
     *
     * @param vertex vertex whose degree is to be calculated.
     *
     * @return the degree of the specified vertex.
     */
    public int adjunctInDegreeOf(V vertex);

    /**
     * Returns a set of all edges incoming into the specified vertex.
     *
     * @param vertex the vertex for which the list of incoming edges to be
     * returned.
     *
     * @return a set of all edges incoming into the specified vertex.
     */
    public Set<E> adjunctIncomingEdgesOf(V vertex);

    /**
     * Returns the "out degree" of the specified vertex. An out degree of a
     * vertex in a directed graph is the number of outward directed edges from
     * that vertex. See <a href="http://mathworld.wolfram.com/Outdegree.html">
     * http://mathworld.wolfram.com/Outdegree.html</a>.
     *
     * @param vertex vertex whose degree is to be calculated.
     *
     * @return the degree of the specified vertex.
     */
    public int adjunctOutDegreeOf(V vertex);

    /**
     * Returns a set of all edges outgoing from the specified vertex.
     *
     * @param vertex the vertex for which the list of outgoing edges to be
     * returned.
     *
     * @return a set of all edges outgoing from the specified vertex.
     */
    public Set<E> adjunctOutgoingEdgesOf(V vertex);
}
