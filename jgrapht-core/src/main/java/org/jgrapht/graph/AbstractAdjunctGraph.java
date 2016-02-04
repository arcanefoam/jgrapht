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
/* ------------------
 * AbstractGraph.java
 * ------------------
 * (C) Copyright 2003-2008, by Barak Naveh and Contributors.
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
package org.jgrapht.graph;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.jgrapht.*;
import org.jgrapht.util.*;

/**
 * The most general implementation of the {@link org.jgrapht.AdjunctGraph}
 * interface. Its subclasses add various restrictions to use the adjunct
 * interface with more specific graphs.
 *
 * @author Horacio Hoyos
 * @param <V> the value type
 * @param <E> the element type
 * @since Nov 28, 2015
 */
public abstract class AbstractAdjunctGraph<V, E>
    extends AbstractBaseGraph<V, E>
    implements AdjunctGraph<V, E>
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5730647889266263861L;

    /** The adjunc specifics. */
    private AdjunctSpecifics<V, E> adjuncSpecifics;

    /** The primary graph. */
    protected UnmodifiableGraph<V,E> primaryGraph;

    /**
     * Instantiates a new abstract adjunct graph.
     *
     * @param primaryGraph the primary graph
     * @param allowMultipleEdges the allow multiple edges
     * @param allowLoops the allow loops
     */
    public AbstractAdjunctGraph(Graph<V, E> primaryGraph, boolean allowMultipleEdges, boolean allowLoops) {
        super(primaryGraph.getEdgeFactory(), allowMultipleEdges, allowLoops);
        this.primaryGraph = new UnmodifiableGraph<V, E>(primaryGraph);
        adjuncSpecifics = (AdjunctSpecifics<V, E>) getSpecifics();
    }

    /* (non-Javadoc)
     * @see org.jgrapht.AdjunctGraph#adjunctContainsEdge(java.lang.Object)
     */
    @Override public boolean adjunctContainsEdge(E e)
    {
        return adjuncSpecifics.adjunctContainsEdge(e);
    }

    /* (non-Javadoc)
     * @see org.jgrapht.AdjunctGraph#adjunctContainsEdge(java.lang.Object, java.lang.Object)
     */
    @Override public boolean adjunctContainsEdge(V sourceVertex, V targetVertex)
    {
        return adjuncSpecifics.getAdjunctEdge(sourceVertex, targetVertex) != null;
    }

    /* (non-Javadoc)
     * @see org.jgrapht.AdjunctGraph#adjunctContainsVertex(java.lang.Object)
     */
    @Override public boolean adjunctContainsVertex(V v)
    {
        return adjuncSpecifics.getAdjunctVertexSet().contains(v);
    }

    /**
     * Adjunct degree of.
     *
     * @param vertex the vertex
     * @return the int
     * @see AdjunctUndirectedGraph#adjunctDegreeOf(Object)
     */
    public int adjunctDegreeOf(V vertex) {
        if (adjunctContainsVertex(vertex)) {
            return super.degreeOf(vertex);
        }
        else {
            throw new IllegalArgumentException(
                "no such vertex in graph: " + vertex.toString());
        }
    }

    /* (non-Javadoc)
     * @see org.jgrapht.AdjunctGraph#adjunctEdgeSet()
     */
    @Override public Set<E> adjunctEdgeSet()
    {
        return adjuncSpecifics.getAdjunctEdgeSet();
    }

    /* (non-Javadoc)
     * @see org.jgrapht.AdjunctGraph#adjunctEdgesOf(java.lang.Object)
     */
    @Override public Set<E> adjunctEdgesOf(V vertex)
    {
        return adjuncSpecifics.adjunctEdgesOf(vertex);
    }

    /**
     * Adjunct incoming edges of.
     *
     * @param vertex the vertex
     * @return the sets the
     * @see DirectAdjunctGraph#adjunctIncomingEdgesOf(Object)
     */
    public Set<E> adjunctIncomingEdgesOf(V vertex) {
        if (adjunctContainsVertex(vertex)) {
            return super.incomingEdgesOf(vertex);
        }
        else {
            throw new IllegalArgumentException(
                "no such vertex in graph: " + vertex.toString());
        }
    }

    /**
     * Adjunct in degree of.
     *
     * @param vertex the vertex
     * @return the int
     * @see DirectAdjunctGraph#adjunctInDegreeOf(Object)
     */
    public int adjunctInDegreeOf(V vertex) {
        if (adjunctContainsVertex(vertex)) {
            return super.inDegreeOf(vertex);
        }
        else {
            throw new IllegalArgumentException(
                "no such vertex in graph: " + vertex.toString());
        }
    }

    /**
     * Adjunct out degree of.
     *
     * @param vertex the vertex
     * @return the int
     * @see DirectAdjunctGraph#adjunctOutDegreeOf(Object)
     */
    public int adjunctOutDegreeOf(V vertex) {
        if (adjunctContainsVertex(vertex)) {
            return super.outDegreeOf(vertex);
        }
        else {
            throw new IllegalArgumentException(
                "no such vertex in graph: " + vertex.toString());
        }
    }

    /**
     * Adjunct outgoing edges of.
     *
     * @param vertex the vertex
     * @return the sets the
     * @see DirectAdjunctGraph#adjunctOutgoingEdgesOf(Object)
     */
    public Set<E> adjunctOutgoingEdgesOf(V vertex)
    {
        if (adjunctContainsVertex(vertex)) {
            return super.outgoingEdgesOf(vertex);
        }
        else {
            throw new IllegalArgumentException(
                "no such vertex in graph: " + vertex.toString());
        }
    }

    /* (non-Javadoc)
     * @see org.jgrapht.AdjunctGraph#adjunctVertexSet()
     */
    @Override public Set<V> adjunctVertexSet()
    {
        return adjuncSpecifics.getAdjunctVertexSet();
    }


    /* (non-Javadoc)
     * @see org.jgrapht.AdjunctGraph#getAdjunctEdge(java.lang.Object, java.lang.Object)
     */
    @Override public E getAdjunctEdge(V sourceVertex, V targetVertex) {
        return adjuncSpecifics.getAdjunctEdge(sourceVertex, targetVertex);
    }

    /* (non-Javadoc)
     * @see org.jgrapht.AdjunctGraph#getPrimaryGraph()
     */
    @Override public Graph<V, E> getPrimaryGraph() {
        return primaryGraph;
    }

    /* (non-Javadoc)
     * @see org.jgrapht.graph.AbstractBaseGraph#removeEdge(java.lang.Object, java.lang.Object)
     */
    @Override public E removeEdge(V sourceVertex, V targetVertex)
    {
        E e = getEdge(sourceVertex, targetVertex);
        if (e != null) {
            if (adjunctContainsEdge(e)) {
                if (!removeEdge(e)) {
                    return null;
                }
            }
            else {
                adjuncSpecifics.removeEdgeFromTouchingVertices(e);
            }
        }
        return e;
    }

    /* (non-Javadoc)
     * @see org.jgrapht.graph.AbstractBaseGraph#setEdgeWeight(java.lang.Object, double)
     */
    @Override public void setEdgeWeight(E e, double weight)
    {
        if (adjunctContainsEdge(e)) {
            assert (e instanceof DefaultWeightedEdge) : e.getClass();
            ((DefaultWeightedEdge) e).weight = weight;
        } else {
            throw new UnsupportedOperationException("the base graph is unmodifiable");
        }
    }

    /**
     * Vertex set.
     *
     * @return the sets the
     * @see Graph#vertexSet()
     */
    @Override public Set<V> vertexSet()
    {
        // In adjunct graphs the vertex set is dynamic so it can't be accessed with a view and can be modified
        return adjuncSpecifics.getVertexSet();
    }

    /**
     * Ensures that the specified edge exists in this graph, or else throws
     * exception.
     *
     * @param e edge
     *
     * @return <code>true</code> if this assertion holds.
     *
     * @throws NullPointerException if specified vertex is <code>null</code>.
     * @throws IllegalArgumentException if specified vertex does not exist in
     * this graph.
     */
    private boolean assertEdgeExist(E e)
    {
        if (containsEdge(e)) {
            return true;
        } else if (e == null) {
            throw new NullPointerException();
        } else {
            throw new IllegalArgumentException(
                "no such edge in graph: " + e.toString());
        }
    }

    /* (non-Javadoc)
     * @see org.jgrapht.graph.AbstractBaseGraph#createDirectedSpecifics()
     */
    @Override protected DirectedSpecifics createDirectedSpecifics()
    {
        return new AdjunctDirectedSpecifics();
    }

    /* (non-Javadoc)
     * @see org.jgrapht.graph.AbstractBaseGraph#createUndirectedSpecifics()
     */
    @Override protected UndirectedSpecifics createUndirectedSpecifics()
    {
        return new AdjunctUndirectedSpecifics();
    }

    /**
     * An adjunct graph specifics extends the DirectedSpecifics to allow tracking of deleted vertices and edges in the
     * primary graph.
     *
     * @author Horacio Hoyos
     */
    private class AdjunctDirectedSpecifics
        extends DirectedSpecifics
        implements AdjunctSpecifics<V, E>
    {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 5654243876545966826L;

        /** The negative edges. */
        private Set<E> negativeEdges = new HashSet<E>();

        /** The negative vertex map directed. */
        private Map<V, DirectedEdgeContainer<V, E>> negativeVertexMapDirected;

        /** The negative vertices. */
        private Set<V> negativeVertices = new HashSet<V>();

        /** The primary vertex map directed. */
        private Map<V, DirectedEdgeContainer<V, E>> primaryVertexMapDirected;

        /**
         * Instantiates a new adjunct directed specifics.
         */
        public AdjunctDirectedSpecifics()
        {
            this(new LinkedHashMap<V, DirectedEdgeContainer<V, E>>(),
                    new LinkedHashMap<V, DirectedEdgeContainer<V, E>>());
        }

        /**
         * Instantiates a new adjunct directed specifics.
         *
         * @param negativeVertexMap the negative vertex map
         * @param primaryVertexMap the primary vertex map
         */
        public AdjunctDirectedSpecifics(
                Map<V, DirectedEdgeContainer<V, E>> negativeVertexMap,
                Map<V, DirectedEdgeContainer<V, E>> primaryVertexMap)
        {
            super();
            this.negativeVertexMapDirected = negativeVertexMap;
            this.primaryVertexMapDirected = primaryVertexMap;
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.DirectedSpecifics#addEdgeToTouchingVertices(java.lang.Object)
         */
        @Override public void addEdgeToTouchingVertices(E e, IntrusiveEdge ie)
        {
            edgeMap.put(e, ie);
            negativeEdges.remove(e); 		// In case edges might be equal()
            V source = getEdgeSource(e);
            V target = getEdgeTarget(e);
            if (adjunctContainsVertex(source)) {
                getEdgeContainer(source).addOutgoingEdge(e);
            }
            else {
                if (!primaryVertexMapDirected.containsKey(source))// add with a lazy edge container entry
                    primaryVertexMapDirected.put(source, null);
                getPrimaryEdgeContainer(source).addOutgoingEdge(e);
            }
            if (adjunctContainsVertex(target)) {
                getEdgeContainer(target).addIncomingEdge(e);
            }
            else {
                if (!primaryVertexMapDirected.containsKey(target))// add with a lazy edge container entry
                primaryVertexMapDirected.put(target, null);
                getPrimaryEdgeContainer(target).addIncomingEdge(e);
            }
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#adjunctContainsEdge(java.lang.Object)
         */
        @Override public boolean adjunctContainsEdge(E e)
        {
            return super.containsEdge(e);
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#adjunctEdgesOf(java.lang.Object)
         */
        @Override public Set<E> adjunctEdgesOf(V vertex)
        {
            return super.edgesOf(vertex);
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.DirectedSpecifics#containsEdge(java.lang.Object)
         */
        @Override public boolean containsEdge(E e)
        {
            if (negativeEdges.contains(e))
                return false;
            return edgeMap.containsKey(e) || primaryGraph.containsEdge(e);
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.UndirectedSpecifics#edgeSet()
         */
        @Override public Set<E> edgeSet() {
            Set<E> edges = new LinkedHashSet<E>(super.edgeSet());
            edges.addAll(primaryGraph.edgeSet());
            edges.removeAll(negativeEdges);
            return Collections.unmodifiableSet(edges);
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.DirectedSpecifics#edgesOf(java.lang.Object)
         */
        @Override public Set<E> edgesOf(V vertex)
        {
            if (adjunctContainsVertex(vertex)) {
                return super.edgesOf(vertex);
            }
            else {
                Set<E> edges = new LinkedHashSet<E>(primaryGraph.edgesOf(vertex));
                DirectedEdgeContainer<V, E> pec = getPrimaryEdgeContainer(vertex);
                if (pec != null) {
                    ArrayUnenforcedSet<E> inAndOut = new ArrayUnenforcedSet<E>(
                            getPrimaryEdgeContainer(vertex).incoming);
                    inAndOut.addAll(getPrimaryEdgeContainer(vertex).outgoing);
                    // we have two copies for each self-loop - remove one of them.
                    if (allowingLoops) {
                        Set<E> loops = getAllEdges(vertex, vertex);

                        for (int i = 0; i < inAndOut.size();) {
                            Object e = inAndOut.get(i);

                            if (loops.contains(e)) {
                                inAndOut.remove(i);
                                loops.remove(e); // so we remove it only once
                            } else {
                                i++;
                            }
                        }
                    }
                    edges.addAll(inAndOut);
                }
                edges.removeAll(negativeEdgesOf(vertex));
                return edges;
            }
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#getAdjunctEdge(java.lang.Object, java.lang.Object)
         */
        @Override public E getAdjunctEdge(V sourceVertex, V targetVertex)
        {
            return super.getEdge(sourceVertex, targetVertex);
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#getAdjunctEdgeSet()
         */
        @Override public Set<E> getAdjunctEdgeSet()
        {
            return super.edgeSet();
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#getAdjunctVertexSet()
         */
        @Override public Set<V> getAdjunctVertexSet()
        {
            return super.getVertexSet();
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.DirectedSpecifics#getAllEdges(java.lang.Object, java.lang.Object)
         */
        @Override public Set<E> getAllEdges(V sourceVertex, V targetVertex)
        {

            if (adjunctContainsVertex(sourceVertex)) {
                return super.getAllEdges(sourceVertex, targetVertex);
            }
            else {
                if (primaryGraph.containsVertex(sourceVertex)
                        && containsVertex(targetVertex)) {
                    Set<E> edges = primaryGraph.getAllEdges(sourceVertex, targetVertex);
                    assert edges != null;
                    DirectedEdgeContainer<V, E> pec = getPrimaryEdgeContainer(sourceVertex);
                    if (pec != null) {
                        Set<E> adjEdges = new ArrayUnenforcedSet<E>();

                        Iterator<E> iter = pec.outgoing.iterator();
                        while (iter.hasNext()) {
                            E e = iter.next();
                            if (getEdgeTarget(e).equals(targetVertex)) {
                                adjEdges.add(e);
                            }
                        }
                        edges.addAll(adjEdges);
                    }
                    edges.removeAll(getAllNegativeEdges(sourceVertex, targetVertex));
                    return edges;

                }
            }
            return null;
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.DirectedSpecifics#getEdge(java.lang.Object, java.lang.Object)
         */
        @Override public E getEdge(V sourceVertex, V targetVertex)
        {
            if (adjunctContainsVertex(sourceVertex)) {	// Avoid adding primary vertices to the adjunct vertexMapDirected
                return super.getEdge(sourceVertex, targetVertex);
            } else {
                if (primaryGraph.containsVertex(sourceVertex)
                        && containsVertex(targetVertex)) {
                    E e2 = primaryGraph.getEdge(sourceVertex, targetVertex);
                    if (e2 == null) {
                        DirectedEdgeContainer<V, E> pec = getPrimaryEdgeContainer(sourceVertex);
                        if (pec != null) {
                            Iterator<E> iter = pec.outgoing.iterator();
                            while (iter.hasNext()) {
                                E e = iter.next();
                                if (getEdgeTarget(e).equals(targetVertex)) {
                                    e2 = e;
                                    break;
                                }
                            }
                        }
                    }
                    if (!negativeEdges.contains(e2)) {
                        return e2;
                    }
                }
            }
            return null;
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.DirectedSpecifics#getEdgeSource(java.lang.Object)
         */
        @Override
        public V getEdgeSource(E e) {
            //assertEdgeExist(e);
            if (adjunctContainsEdge(e)) {
                return super.getEdgeSource(e);
            }
            else {
                return primaryGraph.getEdgeSource(e);
            }
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.DirectedSpecifics#getEdgeTarget(java.lang.Object)
         */
        @Override
        public V getEdgeTarget(E e) {
            if (adjunctContainsEdge(e)) {
                return super.getEdgeTarget(e);
            }
            else {
                return primaryGraph.getEdgeTarget(e);
            }
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.DirectedSpecifics#getVertexSet()
         */
        @Override public Set<V> getVertexSet()
        {
            Set<V> vertices = new LinkedHashSet<V>(super.getVertexSet());
            vertices.addAll(primaryGraph.vertexSet());
            vertices.removeAll(negativeVertices);
            return vertices;
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.DirectedSpecifics#incomingEdgesOf(java.lang.Object)
         */
        @Override public Set<E> incomingEdgesOf(V vertex)
        {
            if (adjunctContainsVertex(vertex)) {	// Avoid adding primary vertices to the adjunct vertexMapDirected
                return super.incomingEdgesOf(vertex);
            }
            else {
                Set<E> edges = new LinkedHashSet<E>(primaryGraph.incomingEdgesOf(vertex));
                DirectedEdgeContainer<V, E> pec = getPrimaryEdgeContainer(vertex);
                if (pec != null) {
                    edges.addAll(new LinkedHashSet<E>(pec.incoming));
                }
                edges.removeAll(negativeIncomingEdgesOf(vertex));
                return edges;
            }

        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.DirectedSpecifics#inDegreeOf(java.lang.Object)
         */
        @Override public int inDegreeOf(V vertex)
        {
            if (adjunctContainsVertex(vertex)) {		// Avoid adding primary vertices to the adjunct vertexMapDirected
                return super.inDegreeOf(vertex);
            }
            else {
                // Degree from primary
                int degree = primaryGraph.inDegreeOf(vertex);
                DirectedEdgeContainer<V, E> pec = getPrimaryEdgeContainer(vertex);
                if (pec != null) {
                    degree += pec.incoming.size();
                }
                degree -= negativeInDegreeOf(vertex);
                return degree;
            }
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.DirectedSpecifics#outDegreeOf(java.lang.Object)
         */
        @Override public int outDegreeOf(V vertex)
        {
            if (adjunctContainsVertex(vertex)) {		// Avoid adding primary vertices to the adjunct vertexMapDirected
                return super.outDegreeOf(vertex);
            }
            else {
                // Degree from primary
                int degree = primaryGraph.outDegreeOf(vertex);
                DirectedEdgeContainer<V, E> pec = getPrimaryEdgeContainer(vertex);
                if (pec != null) {
                    degree +=  pec.outgoing.size();
                }
                degree -= negativeOutDegreeOf(vertex);
                return degree;
            }
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.DirectedSpecifics#outgoingEdgesOf(java.lang.Object)
         */
        @Override public Set<E> outgoingEdgesOf(V vertex)
        {
            if (adjunctContainsVertex(vertex)) {	// Avoid adding primary vertices to the adjunct vertexMapDirected
                return super.outgoingEdgesOf(vertex);
            }
            else {
                Set<E> edges = new LinkedHashSet<E>(primaryGraph.outgoingEdgesOf(vertex));
                DirectedEdgeContainer<V, E> pec = getPrimaryEdgeContainer(vertex);
                if (pec != null) {
                    edges.addAll(new LinkedHashSet<E>(pec.outgoing));
                }
                edges.removeAll(negativeOutgoingEdgesOf(vertex));
                return edges;
            }
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.DirectedSpecifics#removeEdgeFromTouchingVertices(java.lang.Object)
         */
        @Override public void removeEdgeFromTouchingVertices(E e)
        {
            V source = getEdgeSource(e);
            V target = getEdgeTarget(e);
            if (edgeMap.containsKey(e)) {
                if (adjunctContainsVertex(source)
                        && adjunctContainsVertex(target)) {
                    super.removeEdgeFromTouchingVertices(e);
                } else {
                    DirectedEdgeContainer<V, E> pec;
                    if (adjunctContainsVertex(source)) {
                        getEdgeContainer(source).removeOutgoingEdge(e);
                    } else {
                        assert primaryGraph.containsVertex(source);
                        pec = getPrimaryEdgeContainer(source);
                        assert pec != null;
                        pec.removeOutgoingEdge(e);
                    }
                    if (adjunctContainsVertex(target)) {
                        getEdgeContainer(target).removeIncomingEdge(e);
                    } else {
                        assert primaryGraph.containsVertex(target);
                        pec = getPrimaryEdgeContainer(target);
                        assert pec != null;
                        pec.removeIncomingEdge(e);
                    }
                }
                edgeMap.remove(e);
            }
            else {
                assert primaryGraph.containsVertex(source);
                // add with a lazy edge container entry
                if (!negativeVertexMapDirected.containsKey(source))
                    negativeVertexMapDirected.put(source, null);
                getNegativeEdgeContainer(source).addOutgoingEdge(e);
                assert primaryGraph.containsVertex(target);
                // add with a lazy edge container entry
                if (!negativeVertexMapDirected.containsKey(target))
                    negativeVertexMapDirected.put(target, null);
                getNegativeEdgeContainer(target).addIncomingEdge(e);
                negativeEdges.add(e);
            }
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.DirectedSpecifics#removeVertex(java.lang.Object)
         */
        @Override public void removeVertex(V v) {
            if (adjunctContainsVertex(v)) {
                super.removeVertex(v);
            }
            else {
                if (primaryGraph.containsVertex(v)) {
                    negativeVertices.add(v);
                }
            }
        }

        /**
         * Gets the all negative edges.
         *
         * @param sourceVertex the source vertex
         * @param targetVertex the target vertex
         * @return the all negative edges
         */
        private Set<E> getAllNegativeEdges(V sourceVertex, V targetVertex)
        {
            assert !adjunctContainsVertex(sourceVertex);
            primaryGraph.assertVertexExist(sourceVertex);
            DirectedEdgeContainer<V, E> nec = getNegativeEdgeContainer(sourceVertex);
            if ((nec != null)) {
                Set<E> edges = new ArrayUnenforcedSet<E>();

                Iterator<E> iter = nec.outgoing.iterator();
                while (iter.hasNext()) {
                    E e = iter.next();
                    if (getEdgeTarget(e).equals(targetVertex)) {
                        edges.add(e);
                    }
                }
                return edges;
            }
            return Collections.emptySet();
        }

        /**
         * A lazy build of edge container for specified vertex.
         *
         * @param vertex a vertex in this graph.
         *
         * @return EdgeContainer
         */
        private DirectedEdgeContainer<V, E> getNegativeEdgeContainer(V vertex)
        {
            assert !adjunctContainsVertex(vertex);
            primaryGraph.assertVertexExist(vertex);
            DirectedEdgeContainer<V, E> ec = null;
            if (negativeVertexMapDirected.containsKey(vertex)) {
                ec = negativeVertexMapDirected.get(vertex);
                if (ec == null) {
                    ec = new DirectedEdgeContainer<V, E>(edgeSetFactory, vertex);
                    negativeVertexMapDirected.put(vertex, ec);
                }
            }
            return ec;
        }

        /**
         * A lazy build of edge container for specified vertex. A separate map is kept for edges that belong to this
         * graph, but connect vertices in the primary graph.
         *
         * @param vertex a vertex in this graph.
         *
         * @return EdgeContainer
         */
        private DirectedEdgeContainer<V, E> getPrimaryEdgeContainer(V vertex)
        {
            assert !adjunctContainsVertex(vertex);
            primaryGraph.assertVertexExist(vertex);
            DirectedEdgeContainer<V, E> ec = null;
            if (primaryVertexMapDirected.containsKey(vertex)) {
                ec = primaryVertexMapDirected.get(vertex);
                if (ec == null) {
                    ec = new DirectedEdgeContainer<V, E>(edgeSetFactory, vertex);
                    primaryVertexMapDirected.put(vertex, ec);
                }
            }
            return ec;
        }


        /**
         * Negative edges of.
         *
         * @param vertex the vertex
         * @return the sets the
         */
        private Set<E> negativeEdgesOf(V vertex)
        {
            assert !adjunctContainsVertex(vertex);
            primaryGraph.assertVertexExist(vertex);
            DirectedEdgeContainer<V, E> nec = getNegativeEdgeContainer(vertex);
            if (nec != null) {
                ArrayUnenforcedSet<E> inAndOut =
                            new ArrayUnenforcedSet<E>(nec.incoming);
                inAndOut.addAll(nec.outgoing);
                // we have two copies for each self-loop - remove one of them.
                if (allowingLoops) {
                    Set<E> loops = getAllEdges(vertex, vertex);

                    for (int i = 0; i < inAndOut.size();) {
                        Object e = inAndOut.get(i);

                        if (loops.contains(e)) {
                            inAndOut.remove(i);
                            loops.remove(e); // so we remove it only once
                        } else {
                            i++;
                        }
                    }
                }
                return Collections.unmodifiableSet(inAndOut);
            }
            return Collections.emptySet();
        }

        /**
         * Negative incoming edges of.
         *
         * @param vertex the vertex
         * @return the sets the
         */
        private Set<E> negativeIncomingEdgesOf(V vertex)
        {
            assert !adjunctContainsVertex(vertex);
            primaryGraph.assertVertexExist(vertex);
            DirectedEdgeContainer<V, E> pec = getNegativeEdgeContainer(vertex);
            if (pec != null) {
                return pec.getUnmodifiableIncomingEdges();
            }
            return Collections.emptySet();
        }

        /**
         * Negative in degree of.
         *
         * @param vertex the vertex
         * @return the int
         */
        private int negativeInDegreeOf(V vertex)
        {
            assert !adjunctContainsVertex(vertex);
            primaryGraph.assertVertexExist(vertex);
            DirectedEdgeContainer<V, E> pec = getNegativeEdgeContainer(vertex);
            if (pec != null) {
                return pec.incoming.size();
            }
            return 0;
        }

        /**
         * Negative out degree of.
         *
         * @param vertex the vertex
         * @return the int
         */
        private int negativeOutDegreeOf(V vertex)
        {
            assert !adjunctContainsVertex(vertex);
            primaryGraph.assertVertexExist(vertex);
            DirectedEdgeContainer<V, E> pec = getNegativeEdgeContainer(vertex);
            if (pec != null) {
                return pec.outgoing.size();
            }
            return 0;
        }

        /**
         * Negative outgoing edges of.
         *
         * @param vertex the vertex
         * @return the sets the
         */
        private Set<E> negativeOutgoingEdgesOf(V vertex)
        {
            assert !adjunctContainsVertex(vertex);
            primaryGraph.assertVertexExist(vertex);
            DirectedEdgeContainer<V, E> pec = getNegativeEdgeContainer(vertex);
            if (pec != null) {
                return pec.getUnmodifiableOutgoingEdges();
            }
            return Collections.emptySet();
        }

    }


    /**
     * A specifics interface that maintains separate information for elements that belong only to the adjunct graph and
     * edges that connect the adjunct to the primary. It also monitors deletion of elements in the primary graph
     * to mask them, i.e. keep a modified view without modifying the primary graph.
     *
     * @author Horacio Hoyos
     *
     * @param <V> The Vertex type
     * @param <E> The Edge type
     */
    private interface AdjunctSpecifics<V, E>
            extends Specifics<V, E>
    {

        /**
         * Adjunct contains edge.
         *
         * @param e the e
         * @return true, if successful
         * @see AdjunctGraph#adjunctContainsEdge(Object)
         */
        public boolean adjunctContainsEdge(E e);

        /**
         * Adjunct edges of.
         *
         * @param vertex the vertex
         * @return the sets the
         * @see AdjunctGraph#adjunctEdgesOf(Object)
         */
        public Set<E> adjunctEdgesOf(V vertex);

        /**
         * Gets the adjunct edge.
         *
         * @param sourceVertex the source vertex
         * @param targetVertex the target vertex
         * @return the adjunct edge
         * @see AdjunctGraph#getAdjunctEdge(Object, Object)
         */
        public E getAdjunctEdge(V sourceVertex, V targetVertex);

        /**
         * Gets the adjunct edge set.
         *
         * @return the adjunct edge set
         * @see AdjunctGraph#adjunctEdgeSet()
         */
        public Set<E> getAdjunctEdgeSet();

        /**
         * Gets the adjunct vertex set.
         *
         * @return the adjunct vertex set
         * @see AdjunctGraph#getAdjunctVertexSet()
         */
        public Set<V> getAdjunctVertexSet();

//		/**
//         * Gets the all negative edges.
//         *
//         * @param sourceVertex the source vertex
//         * @param targetVertex the target vertex
//         * @return the all negative edges
//         */
//        public Set<E> getAllNegativeEdges(V sourceVertex, V targetVertex);
//
//        /**
//         * Gets the negative edges.
//         *
//         * @return the negative vertex set
//         */
//        public Set<E> getNegativeEdgeSet();
//
//        /**
//         * Gets the negative vertex set.
//         *
//         * @return the negative vertex set
//         */
//        public Set<V> getNegativeVertexSet();
//
//        /**
//         * Negative degree of.
//         *
//         * @param vertex the vertex
//         * @return the int
//         */
//        public int negativeDegreeOf(V vertex);
//
//        /**
//         * Negative edges of.
//         *
//         * @param vertex the vertex
//         * @return the sets the
//         */
//        public Set<E> negativeEdgesOf(V vertex);
//
//        /**
//         * Negative incoming edges of.
//         *
//         * @param vertex the vertex
//         * @return the sets the
//         */
//        public Set<E> negativeIncomingEdgesOf(V vertex);
//
//        /**
//         * Negative in degree of.
//         *
//         * @param vertex the vertex
//         * @return the int
//         */
//        public int negativeInDegreeOf(V vertex);
//
//        /**
//         * Negative out degree of.
//         *
//         * @param vertex the vertex
//         * @return the int
//         */
//        public int negativeOutDegreeOf(V vertex);
//
//        /**
//         * Negative outgoing edges of.
//         *
//         * @param vertex the vertex
//         * @return the sets the
//         */
//        public Set<E> negativeOutgoingEdgesOf(V vertex);

    }

    /**
     * An adjunct graph specifics keeps track of deleted edges and nodes in the primary graph so that it can have a view
     * of a modified primary graph without modifying it.
     *
     * @author Horacio Hoyos
     */
    private class AdjunctUndirectedSpecifics
        extends UndirectedSpecifics
        implements AdjunctSpecifics<V, E>
    {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 3841392502468479003L;

        /** The negative edges. */
        private Set<E> negativeEdges = new HashSet<E>();

        /** The negative vertex map directed. */
        private Map<V, UndirectedEdgeContainer<V, E>> negativeVertexMapUndirected;

        /** The negative vertices. */
        private Set<V> negativeVertices = new HashSet<V>();

        /** The primary vertex map directed. */
        private Map<V, UndirectedEdgeContainer<V, E>> primaryVertexMapUndirected;

        /**
         * Instantiates a new adjunct undirected specifics.
         */
        public AdjunctUndirectedSpecifics()
        {
            this(new LinkedHashMap<V, UndirectedEdgeContainer<V, E>>(),
                    new LinkedHashMap<V, UndirectedEdgeContainer<V, E>>());
        }

        /**
         * Instantiates a new adjunct undirected specifics.
         *
         * @param negativeVertexMap the negative vertex map
         * @param primaryVertexMap the primary vertex map
         */
        public AdjunctUndirectedSpecifics(
                Map<V, UndirectedEdgeContainer<V, E>> negativeVertexMap,
                Map<V, UndirectedEdgeContainer<V, E>> primaryVertexMap)
        {
            super();
            this.negativeVertexMapUndirected = negativeVertexMap;
            this.primaryVertexMapUndirected = primaryVertexMap;
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.UndirectedSpecifics#addEdgeToTouchingVertices(java.lang.Object)
         */
        @Override public void addEdgeToTouchingVertices(E e, IntrusiveEdge ie)
        {
            edgeMap.put(e, ie);
            negativeEdges.remove(e);		// In case edges might be equal()
            V source = getEdgeSource(e);
            V target = getEdgeTarget(e);
            if (adjunctContainsVertex(source)) {
                getEdgeContainer(source).addEdge(e);
            }
            else {
                if (!primaryVertexMapUndirected.containsKey(source))	// add with a lazy edge container entry
                    primaryVertexMapUndirected.put(source, null);
                getPrimaryEdgeContainer(source).addEdge(e);
            }
            if (!source.equals(target)) {
                if (adjunctContainsVertex(target)) {
                    getEdgeContainer(target).addEdge(e);
                }
                else {
                    if (!primaryVertexMapUndirected.containsKey(target)) // add with a lazy edge container entry
                        primaryVertexMapUndirected.put(target, null);
                    getPrimaryEdgeContainer(target).addEdge(e);
                }
            }
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#adjunctContainsEdge(java.lang.Object)
         */
        @Override public boolean adjunctContainsEdge(E e)
        {
            return super.containsEdge(e);
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#adjunctEdgesOf(java.lang.Object)
         */
        @Override public Set<E> adjunctEdgesOf(V vertex)
        {
            return super.edgesOf(vertex);
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.UndirectedSpecifics#containsEdge(java.lang.Object)
         */
        @Override public boolean containsEdge(E e)
        {
            if (negativeEdges.contains(e))
                return false;
            return edgeMap.containsKey(e) || primaryGraph.containsEdge(e);
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.UndirectedSpecifics#degreeOf(java.lang.Object)
         */
        @Override public int degreeOf(V vertex)
        {
            if (adjunctContainsVertex(vertex)) {		// Avoid adding primary vertices to the adjunct vertexMapDirected
                return super.degreeOf(vertex);
            }
            else {
                // Degree from primary
                int degree = primaryGraph.degreeOf(vertex);
                UndirectedEdgeContainer<V, E> pec = getPrimaryEdgeContainer(vertex);
                if (pec != null) {
                    // Add degree from adjunct edges
                    if (allowingLoops) { // then we must count, and add loops twice
                        Set<E> edges = pec.vertexEdges;
                        for (E e : edges) {
                            if (getEdgeSource(e).equals(getEdgeTarget(e))) {
                                degree += 2;
                            } else {
                                degree += 1;
                            }
                        }
                    }
                    else {
                        degree += pec.edgeCount();
                    }
                    degree -= negativeDegreeOf(vertex);
                }
                return degree;
            }
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.UndirectedSpecifics#edgeSet()
         */
        @Override public Set<E> edgeSet() {
            Set<E> edges = new LinkedHashSet<E>(super.edgeSet());
            edges.addAll(primaryGraph.edgeSet());
            edges.removeAll(negativeEdges);
            return Collections.unmodifiableSet(edges);
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.UndirectedSpecifics#edgesOf(java.lang.Object)
         */
        @Override public Set<E> edgesOf(V vertex)
        {
            if (adjunctContainsVertex(vertex)) {
                return super.edgesOf(vertex);
            }
            else {
                Set<E> edges = new LinkedHashSet<E>(primaryGraph.edgesOf(vertex));
                UndirectedEdgeContainer<V, E> pec = getPrimaryEdgeContainer(vertex);
                if (pec != null) {
                    edges.addAll(getPrimaryEdgeContainer(vertex).vertexEdges);
                }
                edges.removeAll(negativeEdgesOf(vertex));
                return edges;
            }
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#getAdjunctEdge(java.lang.Object, java.lang.Object)
         */
        @Override public E getAdjunctEdge(V sourceVertex, V targetVertex)
        {
            return super.getEdge(sourceVertex, targetVertex);
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#getAdjunctEdgeSet()
         */
        @Override public Set<E> getAdjunctEdgeSet()
        {
            return super.edgeSet();
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#getAdjunctVertexSet()
         */
        @Override public Set<V> getAdjunctVertexSet()
        {
            return super.getVertexSet();
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.UndirectedSpecifics#getAllEdges(java.lang.Object, java.lang.Object)
         */
        @Override public Set<E> getAllEdges(V sourceVertex, V targetVertex)
        {
            if (adjunctContainsVertex(sourceVertex)) {
                return super.getAllEdges(sourceVertex, targetVertex);
            }
            else {
                if (primaryGraph.containsVertex(sourceVertex)
                        && containsVertex(targetVertex)) {
                    Set<E> edges = primaryGraph.getAllEdges(sourceVertex, targetVertex);
                    assert edges != null;
                    UndirectedEdgeContainer<V, E> pec = getPrimaryEdgeContainer(sourceVertex);
                    if (pec != null) {
                        Set<E> adjEdges = new ArrayUnenforcedSet<E>();
                        Iterator<E> iter = pec.vertexEdges.iterator();
                        while (iter.hasNext()) {
                            E e = iter.next();
                            boolean equal =
                                    isEqualsStraightOrInverted(
                                        sourceVertex,
                                        targetVertex,
                                        e);
                            if (equal) {
                                adjEdges.add(e);
                            }
                        }
                        edges.addAll(adjEdges);
                    }
                    edges.removeAll(getAllNegativeEdges(sourceVertex, targetVertex));
                    return edges;
                }
            }
            return null;
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.UndirectedSpecifics#getEdge(java.lang.Object, java.lang.Object)
         */
        @Override public E getEdge(V sourceVertex, V targetVertex)
        {
            if (adjunctContainsVertex(sourceVertex)) {	// Avoid adding primary vertices to the adjunct vertexMapDirected
                return super.getEdge(sourceVertex, targetVertex);
            }
            else {
                if (primaryGraph.containsVertex(sourceVertex)
                        && containsVertex(targetVertex)) {
                    E e2 = primaryGraph.getEdge(sourceVertex, targetVertex);
                    if (e2 == null) {
                        UndirectedEdgeContainer<V, E> pec = getPrimaryEdgeContainer(sourceVertex);
                        if (pec != null) {
                            Iterator<E> iter = pec.vertexEdges.iterator();
                            while (iter.hasNext()) {
                                E e = iter.next();

                                boolean equal =
                                    isEqualsStraightOrInverted(
                                        sourceVertex,
                                        targetVertex,
                                        e);

                                if (equal) {
                                    e2 = e;
                                    break;
                                }
                            }
                        }
                    }
                    if (!negativeEdges.contains(e2)) {
                        return e2;
                    }
                }
            }
            return null;
        }


        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.UndirectedSpecifics#getEdgeSource(java.lang.Object)
         */
        @Override
        public V getEdgeSource(E e) {
            //assertEdgeExist(e);
            if (adjunctContainsEdge(e)) {
                return super.getEdgeSource(e);
            }
            else {
                return primaryGraph.getEdgeSource(e);
            }
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.UndirectedSpecifics#getEdgeTarget(java.lang.Object)
         */
        @Override
        public V getEdgeTarget(E e) {
            //assertEdgeExist(e);
            if (adjunctContainsEdge(e)) {
                return super.getEdgeTarget(e);
            }
            else {
                return primaryGraph.getEdgeTarget(e);
            }
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.UndirectedSpecifics#getVertexSet()
         */
        @Override public Set<V> getVertexSet()
        {
            Set<V> vertices = new LinkedHashSet<V>(super.getVertexSet());
            vertices.addAll(primaryGraph.vertexSet());
            vertices.removeAll(negativeVertices);
            return vertices;
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.UndirectedSpecifics#removeEdgeFromTouchingVertices(java.lang.Object)
         */
        @Override public void removeEdgeFromTouchingVertices(E e)
        {
            V source = getEdgeSource(e);
            V target = getEdgeTarget(e);
            if (edgeMap.containsKey(e)) {
                if (adjunctContainsVertex(source)
                        && adjunctContainsVertex(target)) {
                    super.removeEdgeFromTouchingVertices(e);
                } else {
                    UndirectedEdgeContainer<V, E> pec;
                    if (adjunctContainsVertex(source)) {
                        getEdgeContainer(source).removeEdge(e);
                    } else {
                        assert primaryGraph.containsVertex(source);
                        pec = getPrimaryEdgeContainer(source);
                        assert pec != null;
                        pec.removeEdge(e);
                    }
                    if (!source.equals(target)) {
                        if (adjunctContainsVertex(target)) {
                            getEdgeContainer(target).removeEdge(e);
                        } else {
                            assert primaryGraph.containsVertex(target);
                            pec = getPrimaryEdgeContainer(target);
                            assert pec != null;
                            pec.removeEdge(e);
                        }
                    }
                }
                edgeMap.remove(e);
            }
            else {
                assert primaryGraph.containsVertex(source);
                // add with a lazy edge container entry
                if (!negativeVertexMapUndirected.containsKey(source))
                    negativeVertexMapUndirected.put(source, null);
                getNegativeEdgeContainer(source).addEdge(e);
                if (!source.equals(target)) {
                    assert primaryGraph.containsVertex(target);
                    if (!negativeVertexMapUndirected.containsKey(target))
                        negativeVertexMapUndirected.put(target, null);
                    getNegativeEdgeContainer(target).addEdge(e);
                }
                negativeEdges.add(e);
            }
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.UndirectedSpecifics#removeVertex(java.lang.Object)
         */
        @Override public void removeVertex(V v) {
            if (adjunctContainsVertex(v)) {
                super.removeVertex(v);
            }
            else {
                if (primaryGraph.containsVertex(v)) {
                    negativeVertices.add(v);
                }
            }
        }

        /**
         * Gets the all negative edges.
         *
         * @param sourceVertex the source vertex
         * @param targetVertex the target vertex
         * @return the all negative edges
         */
        private Set<E> getAllNegativeEdges(V sourceVertex, V targetVertex) {

            assert !adjunctContainsVertex(sourceVertex);
            primaryGraph.assertVertexExist(sourceVertex);
            UndirectedEdgeContainer<V, E> nec = getNegativeEdgeContainer(sourceVertex);
            if ((nec != null) && containsVertex(targetVertex)) {
                Set<E> edges = new ArrayUnenforcedSet<E>();
                Iterator<E> iter = nec.vertexEdges.iterator();
                while (iter.hasNext()) {
                    E e = iter.next();
                    boolean equal =
                            isEqualsStraightOrInverted(
                                sourceVertex,
                                targetVertex,
                                e);
                    if (equal) {
                        edges.add(e);
                    }
                }
                return edges;
            }
            return Collections.emptySet();
        }

        /**
         * A lazy build of edge container for specified vertex.
         *
         * @param vertex a vertex in this graph.
         *
         * @return EdgeContainer
         */
        private UndirectedEdgeContainer<V, E> getNegativeEdgeContainer(V vertex)
        {
            assert !adjunctContainsVertex(vertex);
            primaryGraph.assertVertexExist(vertex);
            // We need to test if the key exists, as having a vertex in the map means it
            // is deleted, even if the ec is null
            UndirectedEdgeContainer<V, E> ec = null;
            if (negativeVertexMapUndirected.containsKey(vertex)) {
                ec = negativeVertexMapUndirected.get(vertex);
                if (ec == null) {
                    ec = new UndirectedEdgeContainer<V, E>(edgeSetFactory, vertex);
                    negativeVertexMapUndirected.put(vertex, ec);
                }
            }
            return ec;

        }

        /**
         * A lazy build of edge container for specified vertex. A separate map is kept for edges that belong to this
         * graph, but connect vertices in the primary graph.
         *
         * @param vertex a vertex in this graph.
         *
         * @return EdgeContainer
         */
        private UndirectedEdgeContainer<V, E> getPrimaryEdgeContainer(V vertex)
        {
            assert !adjunctContainsVertex(vertex);
            primaryGraph.assertVertexExist(vertex);
            // Keep the map small by only adding adjuncted vertices
            UndirectedEdgeContainer<V, E> ec = null;
            if (primaryVertexMapUndirected.containsKey(vertex)) {
                ec = primaryVertexMapUndirected.get(vertex);
                if (ec == null) {
                    ec = new UndirectedEdgeContainer<V, E>(edgeSetFactory, vertex);
                    primaryVertexMapUndirected.put(vertex, ec);
                }
            }
            return ec;

        }

        /**
         * Negative degree of.
         *
         * @param vertex the vertex
         * @return the int
         */
        private int negativeDegreeOf(V vertex)
        {
            assert !adjunctContainsVertex(vertex);
            primaryGraph.assertVertexExist(vertex);
            UndirectedEdgeContainer<V, E> nec = getNegativeEdgeContainer(vertex);
            if (nec != null) {
                if (allowingLoops) { // then we must count, and add loops twice
                    int degree = 0;
                    if (nec != null) {
                        Set<E> edges = nec.vertexEdges;
                        for (E e : edges) {
                            if (getEdgeSource(e).equals(getEdgeTarget(e))) {
                                degree += 2;
                            } else {
                                degree += 1;
                            }
                        }
                    }
                    return degree;
                } else {
                    return nec.edgeCount();
                }
            }
            return 0;
        }

        /**
         * Negative edges of.
         *
         * @param vertex the vertex
         * @return the sets the
         */
        private Set<E> negativeEdgesOf(V vertex)
        {
            assert !adjunctContainsVertex(vertex);
            primaryGraph.assertVertexExist(vertex);
            UndirectedEdgeContainer<V, E> ec = getNegativeEdgeContainer(vertex);
            if (ec != null) {
                return ec.getUnmodifiableVertexEdges();
            }
            return Collections.emptySet();
        }



    }

}
