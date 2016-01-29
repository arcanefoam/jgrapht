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
 *
 */
package org.jgrapht.graph;

import java.util.LinkedHashSet;
import java.util.Set;

import org.jgrapht.*;


/**
 * The Class SimpleDirectedAdjunctGraph is the simplest implementation of a directed adjunct graph.
 *
 * @param <V> the value type
 * @param <E> the element type
 */
public class SimpleDirectedAdjunctGraph<V, E>
    extends AbstractAdjunctGraph<V, E>
    implements DirectedGraph<V, E>, DirectAdjunctGraph<V, E>
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5296261373996749227L;


    /**
     * Instantiates a new simple directed adjunct graph.
     *
     * @param base the base
     */
    public SimpleDirectedAdjunctGraph(DirectedGraph<V, E> primaryGraph) {
        super(primaryGraph, false, false);
        //this.primaryGraph = primaryGraph;
    }


    /* (non-Javadoc)
     * @see org.jgrapht.graph.AbstractBaseGraph#degreeOf(java.lang.Object)
     */
    @Override public int degreeOf(V vertex)
    {
        throw new UnsupportedOperationException(DirectedSpecifics.NOT_IN_DIRECTED_GRAPH);
    }

    /* (non-Javadoc)
     * @see org.jgrapht.graph.AbstractBaseGraph#inDegreeOf(java.lang.Object)
     */
    @Override public int inDegreeOf(V vertex)
    {
        int degree = super.inDegreeOf(vertex);
        if (primaryGraph.containsVertex(vertex))
            degree += primaryGraph.inDegreeOf(vertex);
        return degree;
    }

    /* (non-Javadoc)
     * @see org.jgrapht.graph.AbstractBaseGraph#incomingEdgesOf(java.lang.Object)
     */
    @Override public Set<E> incomingEdgesOf(V vertex)
    {
        Set<E> incoming = new LinkedHashSet<E>(super.incomingEdgesOf(vertex));
        if (primaryGraph.containsVertex(vertex))
            incoming.addAll(primaryGraph.incomingEdgesOf(vertex));
        return incoming;
    }

    /* (non-Javadoc)
     * @see org.jgrapht.graph.AbstractBaseGraph#outDegreeOf(java.lang.Object)
     */
    @Override public int outDegreeOf(V vertex)
    {
        int degree = super.outDegreeOf(vertex);
        if (primaryGraph.containsVertex(vertex))
            degree += primaryGraph.outDegreeOf(vertex);
        return degree;
    }

    /* (non-Javadoc)
     * @see org.jgrapht.graph.AbstractBaseGraph#outgoingEdgesOf(java.lang.Object)
     */
    @Override public Set<E> outgoingEdgesOf(V vertex)
    {
        Set<E> outgoing = new LinkedHashSet<E>(super.outgoingEdgesOf(vertex));
        if (primaryGraph.containsVertex(vertex))
            outgoing.addAll(primaryGraph.outgoingEdgesOf(vertex));
        return outgoing;
    }


    /* (non-Javadoc)
     * @see org.jgrapht.DirectAdjunctGraph#adjunctInDegreeOf(java.lang.Object)
     */
    @Override public int adjunctInDegreeOf(V vertex) {
        return super.inDegreeOf(vertex);
    }


    /* (non-Javadoc)
     * @see org.jgrapht.DirectAdjunctGraph#adjunctIncomingEdgesOf(java.lang.Object)
     */
    @Override public Set<E> adjunctIncomingEdgesOf(V vertex) {
        return super.incomingEdgesOf(vertex);
    }


    /* (non-Javadoc)
     * @see org.jgrapht.DirectAdjunctGraph#adjunctOutDegreeOf(java.lang.Object)
     */
    @Override public int adjunctOutDegreeOf(V vertex) {
        return super.outDegreeOf(vertex);
    }


    /* (non-Javadoc)
     * @see org.jgrapht.DirectAdjunctGraph#adjunctOutgoingEdgesOf(java.lang.Object)
     */
    @Override public Set<E> adjunctOutgoingEdgesOf(V vertex) {
        return super.outgoingEdgesOf(vertex);
    }

}
