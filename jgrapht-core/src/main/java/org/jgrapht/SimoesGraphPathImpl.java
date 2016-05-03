package org.jgrapht;
/* ==========================================
 * JGraphT : a free Java graph-theory library
 * ==========================================
 *
 * Project Info:  http://jgrapht.sourceforge.net/
 * Project Creator:  Barak Naveh (http://sourceforge.net/users/barak_naveh)
 *
 * (C) Copyright 2003-2008, by Barak Naveh and Contributors.
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
/* -------------------------
 * SimoesAllPaths.java
 * -------------------------
 * (C) Copyright 2016, by Horacio Hoyos
 *
 * Original Author:  Guillaume Boulmier and Contributors.
 * Contributor(s):   Horacio Hoyos
 *
 * $Id$
 *
 * Changes
 * -------
 * 30-Apr-2016 : Initial revision (GB);
 *
 */

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Class SimoesGraphPathImpl.
 *
 * @param <V> the value type
 * @param <E> the element type
 */
public class SimoesGraphPathImpl<V, E> implements SimoesGraphPath<V, E> {

    /** The graph. */
    private DirectedGraph<V, E> graph;

    /** The edge list. */
    private List<E> edgeList;

    /** The unmodifiable edge list. */
    private List<E> unmodifiableEdgeList = null;

    /** The start vertex. */
    private V startVertex;

    /** The end vertex. */
    private V endVertex;

    /** The weight. */
    private double weight;

    /** The has loop. */
    private boolean hasLoop = false;

    /** The loop vertex. */
    private V loopVertex;


    /**
     * Instantiates a new dependency path impl.
     *
     * @param graph the graph
     * @param e the e
     */
    public SimoesGraphPathImpl(DirectedGraph<V, E> graph, E e) {
        this.graph = graph;
        this.edgeList = new ArrayList<E>();
        edgeList.add(e);
        startVertex = graph.getEdgeSource(e);
        weight = 1;
        endVertex = graph.getEdgeTarget(e);
    }

    /**
     * Instantiates a new simoes graph path impl.
     *
     * @param graph the graph
     * @param edgeList the edge list
     */
    public SimoesGraphPathImpl(DirectedGraph<V, E> graph, List<E> edgeList) {
        super();
        this.graph = graph;
        this.edgeList = new ArrayList<E>(edgeList);
        startVertex = graph.getEdgeSource(edgeList.get(0));
        weight = edgeList.size();
        endVertex = graph.getEdgeTarget(edgeList.get((int) (weight-1)));
    }

    /* (non-Javadoc)
     * @see org.jgrapht.SimoesGraphPath#addEdge(java.lang.Object)
     */
    @Override
    public void addEdge(E e) {
        if (!graph.getEdgeSource(e).equals(endVertex)) {
            throw new InvalidParameterException("The added edge must extend the path. " + graph.getEdgeSource(e) + " does not match "
                    + endVertex);
        }
        edgeList.add(e);
        endVertex = graph.getEdgeTarget(e);
        weight = edgeList.size();
    }

    /* (non-Javadoc)
     * @see org.jgrapht.SimoesGraphPath#contains(java.lang.Object)
     */
    @Override
    public boolean contains(V vertex) {
        return Graphs.getPathVertexList(this).contains(vertex);
    }


    /* (non-Javadoc)
     * @see org.jgrapht.GraphPath#getEdgeList()
     */
    @Override
    public List<E> getEdgeList() {
        if (unmodifiableEdgeList == null) {
            unmodifiableEdgeList = Collections.unmodifiableList(edgeList);
        }
        return unmodifiableEdgeList;
    }

    /* (non-Javadoc)
     * @see org.jgrapht.GraphPath#getEndVertex()
     */
    @Override
    public V getEndVertex() {
        return endVertex;
    }

    /* (non-Javadoc)
     * @see org.jgrapht.GraphPath#getGraph()
     */
    @Override
    public Graph<V, E> getGraph() {
        return graph;
    }

    /* (non-Javadoc)
     * @see org.jgrapht.SimoesGraphPath#getLoopVertex()
     */
    public V getLoopVertex() {
        return loopVertex;
    }

    /* (non-Javadoc)
     * @see org.jgrapht.GraphPath#getStartVertex()
     */
    @Override
    public V getStartVertex() {
        return startVertex;
    }

    /* (non-Javadoc)
     * @see org.jgrapht.GraphPath#getWeight()
     */
    @Override
    public double getWeight() {
        return weight;
    }

    /* (non-Javadoc)
     * @see org.jgrapht.SimoesGraphPath#hasLoop()
     */
    @Override
    public boolean hasLoop() {
        return hasLoop;
    }

    /* (non-Javadoc)
     * @see org.jgrapht.SimoesGraphPath#setLoopVertex(java.lang.Object)
     */
    public void setLoopVertex(V loopVertex) {
        hasLoop = true;
        this.loopVertex = loopVertex;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    // override Object
    @Override public String toString()
    {
        return edgeList.toString() + (hasLoop ? "*" : "");
    }

}
