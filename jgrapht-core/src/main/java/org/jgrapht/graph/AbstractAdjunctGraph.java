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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.jgrapht.*;
import org.jgrapht.util.*;

// TODO: Auto-generated Javadoc
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

    /** The primary graph. */
    protected UnmodifiableGraph<V,E> primaryGraph;

    /** The adjunc specifics. */
    private AdjunctSpecifics<V, E> adjuncSpecifics;

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
     * @see org.jgrapht.graph.AbstractBaseGraph#addEdge(java.lang.Object, java.lang.Object)
     */
    @Override public E addEdge(V sourceVertex, V targetVertex)
    {
        return super.addEdge(sourceVertex, targetVertex);
    }

    /* (non-Javadoc)
     * @see org.jgrapht.graph.AbstractBaseGraph#addEdge(java.lang.Object, java.lang.Object, java.lang.Object)
     */
    @Override public boolean addEdge(V sourceVertex, V targetVertex, E e)
    {
        return super.addEdge(sourceVertex, targetVertex, e);
    }

    /* (non-Javadoc)
     * @see org.jgrapht.AdjunctGraph#adjunctContainsEdge(java.lang.Object)
     */
    @Override public boolean adjunctContainsEdge(E e)
    {
        return super.containsEdge(e);
    }

    /* (non-Javadoc)
     * @see org.jgrapht.AdjunctGraph#adjunctContainsEdge(java.lang.Object, java.lang.Object)
     */
    @Override public boolean adjunctContainsEdge(V sourceVertex, V targetVertex)
    {
        return super.containsEdge(sourceVertex, targetVertex);
    }

    /* (non-Javadoc)
     * @see org.jgrapht.AdjunctGraph#adjunctContainsVertex(java.lang.Object)
     */
    @Override public boolean adjunctContainsVertex(V v)
    {
        return super.containsVertex(v);
    }

    /* (non-Javadoc)
     * @see org.jgrapht.AdjunctGraph#adjunctEdgeSet()
     */
    @Override public Set<E> adjunctEdgeSet()
    {
        return super.edgeSet();
    }

    /* (non-Javadoc)
     * @see org.jgrapht.AdjunctGraph#adjunctEdgesOf(java.lang.Object)
     */
    @Override public Set<E> adjunctEdgesOf(V vertex)
    {
        return super.edgeSet();
    }

    /* (non-Javadoc)
     * @see org.jgrapht.AdjunctGraph#adjunctVertexSet()
     */
    @Override public Set<V> adjunctVertexSet()
    {
        return super.vertexSet();
    }

    /* (non-Javadoc)
     * @see org.jgrapht.graph.AbstractBaseGraph#containsEdge(java.lang.Object)
     */
    @Override public boolean containsEdge(E e)
    {
        if (adjuncSpecifics.getNegativeEdgeSet().contains(e)) {
            return false;
        }
        boolean contains = super.containsEdge(e);
        if (!contains /*&& jointAccess*/)
            contains = primaryGraph.containsEdge(e);
        return contains;
    }

    /* (non-Javadoc)
     * @see org.jgrapht.graph.AbstractGraph#containsEdge(java.lang.Object, java.lang.Object)
     */
    @Override public boolean containsEdge(V sourceVertex, V targetVertex)
    {
        return super.containsEdge(sourceVertex, targetVertex);
    }

    /* (non-Javadoc)
     * @see org.jgrapht.graph.AbstractBaseGraph#containsVertex(java.lang.Object)
     */
    @Override public boolean containsVertex(V v)
    {
        boolean contains = super.containsVertex(v);
        if (!contains /*&& jointAccess*/)
            contains = primaryGraph.containsVertex(v);
            if (contains) {
                return !adjuncSpecifics.getNegativeVertexSet().contains(v);
            }
        return contains;
    }

    /* (non-Javadoc)
     * @see org.jgrapht.graph.AbstractBaseGraph#edgeSet()
     */
    @Override public Set<E> edgeSet()
    {

        Set<E> edges = new LinkedHashSet<E>(super.edgeSet());
        edges.addAll(primaryGraph.edgeSet());
        edges.removeAll(adjuncSpecifics.getNegativeEdgeSet());
        return Collections.unmodifiableSet(edges);
    }

    /* (non-Javadoc)
     * @see org.jgrapht.graph.AbstractBaseGraph#edgesOf(java.lang.Object)
     */
    @Override public Set<E> edgesOf(V vertex)
    {

        Set<E> edges = new LinkedHashSet<E>(super.edgesOf(vertex));
        edges.addAll(primaryGraph.edgesOf(vertex));
        edges.removeAll(adjuncSpecifics.negativeEdgesOf(vertex));
        return Collections.unmodifiableSet(edges);
    }

    /* (non-Javadoc)
     * @see org.jgrapht.graph.AbstractBaseGraph#getAllEdges(java.lang.Object, java.lang.Object)
     */
    @Override public Set<E> getAllEdges(V sourceVertex, V targetVertex)
    {
        Set<E> edges = super.getAllEdges(sourceVertex, targetVertex);
        edges.addAll(primaryGraph.getAllEdges(sourceVertex, targetVertex));
        edges.removeAll(adjuncSpecifics.getAllNegativeEdges(sourceVertex, targetVertex));
        return edges;
    }

    /* (non-Javadoc)
     * @see org.jgrapht.graph.AbstractBaseGraph#getEdge(java.lang.Object, java.lang.Object)
     */
    @Override public E getEdge(V sourceVertex, V targetVertex)
    {

        E baseEdge  = super.getEdge(sourceVertex, targetVertex);
        if (baseEdge == null) {
            baseEdge = primaryGraph.getEdge(sourceVertex, targetVertex);
            if (adjuncSpecifics.getNegativeEdgeSet().contains(baseEdge))
                return null;
        }
        return baseEdge;
    }

    /* (non-Javadoc)
     * @see org.jgrapht.graph.AbstractBaseGraph#getEdgeSource(java.lang.Object)
     */
    @Override public V getEdgeSource(E e)
    {
        if (adjunctContainsEdge(e)) {
            return super.getEdgeSource(e);
        } else {
            V source = primaryGraph.getEdgeSource(e);
            if (adjuncSpecifics.getNegativeVertexSet().contains(source)) {
                return null;
            }
            return source;
        }

    }

    /* (non-Javadoc)
     * @see org.jgrapht.graph.AbstractBaseGraph#getEdgeTarget(java.lang.Object)
     */
    @Override public V getEdgeTarget(E e)
    {
        if (adjunctContainsEdge(e)) {
            return super.getEdgeTarget(e);
        } else {
            V target = primaryGraph.getEdgeTarget(e);
            if (adjuncSpecifics.getNegativeVertexSet().contains(target)) {
                return null;
            }
            return target;
        }
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


    @Override public boolean removeVertex(V v)
    {
        if (!adjunctContainsVertex(v) && primaryGraph.containsVertex(v)) {
            adjuncSpecifics.removePrimaryVertex(v); // Do first so entry for v is added to negative map
        }
        return super.removeVertex(v);
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

    /* (non-Javadoc)
     * @see org.jgrapht.graph.AbstractBaseGraph#vertexSet()
     */
    @Override public Set<V> vertexSet() {

        Set<V> vertices = new LinkedHashSet<V>(super.vertexSet());
        vertices.addAll(primaryGraph.vertexSet());
        vertices.removeAll(adjuncSpecifics.getNegativeVertexSet());
        return Collections.unmodifiableSet(vertices);
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
        @Override public void addEdgeToTouchingVertices(E e)
        {
            V source = getEdgeSource(e);
            V target = getEdgeTarget(e);
            if (adjunctContainsVertex(source)) {
                getEdgeContainer(source).addOutgoingEdge(e);
            }
            else {
                getPrimaryEdgeContainer(source).addOutgoingEdge(e);
            }
            if (adjunctContainsVertex(target)) {
                getEdgeContainer(target).addOutgoingEdge(e);
            }
            else {
                getPrimaryEdgeContainer(target).addOutgoingEdge(e);
            }
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
                ArrayUnenforcedSet<E> inAndOut =
                        new ArrayUnenforcedSet<E>(getPrimaryEdgeContainer(vertex).incoming);
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
                return Collections.unmodifiableSet(inAndOut);
            }
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.DirectedSpecifics#getAllEdges(java.lang.Object, java.lang.Object)
         */
        @Override public Set<E> getAllEdges(V sourceVertex, V targetVertex)
        {
            Set<E> edges = super.getAllEdges(sourceVertex, targetVertex);
            if (edges != null) { // containsVertex(sourceVertex) && containsVertex(targetVertex))
                if (!adjunctContainsVertex(sourceVertex)) {
                    Set<E> primaryEdges = new ArrayUnenforcedSet<E>();
                    Iterator<E> iter = getPrimaryEdgeContainer(sourceVertex).outgoing.iterator();
                    while (iter.hasNext()) {
                        E e = iter.next();

                        if (getEdgeTarget(e).equals(targetVertex)) {
                            primaryEdges.add(e);
                        }
                    }
                    edges.addAll(primaryEdges);
                }
            }
            return edges;
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#getAllNegativeEdges(java.lang.Object, java.lang.Object)
         */
        @Override
        public Set<E> getAllNegativeEdges(V sourceVertex, V targetVertex)
        {
            if (adjunctContainsVertex(sourceVertex)
                    && containsVertex(targetVertex)
                    && negativeVertexMapDirected.containsKey(sourceVertex)) {
                Set<E> edges = new ArrayUnenforcedSet<E>();

                Iterator<E> iter = getNegativeEdgeContainer(sourceVertex).outgoing.iterator();
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

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.DirectedSpecifics#getEdge(java.lang.Object, java.lang.Object)
         */
        @Override public E getEdge(V sourceVertex, V targetVertex)
        {
            if (adjunctContainsVertex(sourceVertex)) {	// Avoid adding primary vertices to the adjunct vertexMapDirected
                return super.getEdge(sourceVertex, targetVertex);
            } else {
                if (containsVertex(sourceVertex) && containsVertex(targetVertex)) {
                    DirectedEdgeContainer<V, E> ec = getPrimaryEdgeContainer(sourceVertex);
                    Iterator<E> iter = ec.outgoing.iterator();
                    while (iter.hasNext()) {
                        E e = iter.next();
                        if (getEdgeTarget(e).equals(targetVertex)) {
                            return e;
                        }
                    }
                }
            }
            return null;
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#getNegativeEdgeSet()
         */
        @Override
        public Set<E> getNegativeEdgeSet() {
            return negativeEdges;
        }


        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#getNegativeVertexSet()
         */
        @Override
        public Set<V> getNegativeVertexSet()
        {
            return negativeVertexMapDirected.keySet();
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.DirectedSpecifics#incomingEdgesOf(java.lang.Object)
         */
        @Override public Set<E> incomingEdgesOf(V vertex)
        {

            Set<E> edges = new LinkedHashSet<E>();
            if (adjunctContainsVertex(vertex)) {	// Avoid adding primary vertices to the adjunct vertexMapDirected
                edges.addAll(super.incomingEdgesOf(vertex));
            }
            else {
                edges.addAll(new LinkedHashSet<E>(getPrimaryEdgeContainer(vertex).incoming));
            }
            return edges;
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.DirectedSpecifics#inDegreeOf(java.lang.Object)
         */
        @Override public int inDegreeOf(V vertex)
        {
            if (adjunctContainsVertex(vertex)) {		// Avoid adding primary vertices to the adjunct vertexMapDirected
                return super.degreeOf(vertex);
            }
            else {
                return getPrimaryEdgeContainer(vertex).incoming.size();
            }
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#negativeDegreeOf(java.lang.Object)
         */
        @Override public int negativeDegreeOf(V vertex)
        {
            throw new UnsupportedOperationException(NOT_IN_DIRECTED_GRAPH);
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#negativeEdgesOf(java.lang.Object)
         */
        @Override public Set<E> negativeEdgesOf(V vertex)
        {
            if (!adjunctContainsVertex(vertex)) {	// Avoid adding adjunct vertices to the adjunct negativeVertexMapDirected
                ArrayUnenforcedSet<E> inAndOut =
                            new ArrayUnenforcedSet<E>(getNegativeEdgeContainer(vertex).incoming);
                inAndOut.addAll(getNegativeEdgeContainer(vertex).outgoing);

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

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#negativeIncomingEdgesOf(java.lang.Object)
         */
        @Override public Set<E> negativeIncomingEdgesOf(V vertex)
        {
            if (!adjunctContainsVertex(vertex)) {	// Avoid adding adjunct vertices to the adjunct negativeVertexMapDirected
                return getNegativeEdgeContainer(vertex).getUnmodifiableIncomingEdges();
            }
            return Collections.emptySet();
        }


        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#negativeInDegreeOf(java.lang.Object)
         */
        @Override public int negativeInDegreeOf(V vertex)
        {
            if (!adjunctContainsVertex(vertex)) {	// Avoid adding adjunct vertices to the adjunct negativeVertexMapDirected
                return getNegativeEdgeContainer(vertex).incoming.size();
            }
            return 0;
        }


        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#negativeOutDegreeOf(java.lang.Object)
         */
        @Override public int negativeOutDegreeOf(V vertex)
        {
            if (!adjunctContainsVertex(vertex)) {	// Avoid adding adjunct vertices to the adjunct negativeVertexMapDirected
                return getNegativeEdgeContainer(vertex).outgoing.size();
            }
            return 0;
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#negativeOutgoingEdgesOf(java.lang.Object)
         */
        @Override public Set<E> negativeOutgoingEdgesOf(V vertex)
        {
            if (!adjunctContainsVertex(vertex)) {	// Avoid adding adjunct vertices to the adjunct negativeVertexMapDirected
                return getNegativeEdgeContainer(vertex).getUnmodifiableOutgoingEdges();
            }
            return Collections.emptySet();
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
                return getPrimaryEdgeContainer(vertex).outgoing.size();
            }
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.DirectedSpecifics#outgoingEdgesOf(java.lang.Object)
         */
        @Override public Set<E> outgoingEdgesOf(V vertex)
        {
            Set<E> edges = new LinkedHashSet<E>();
            if (adjunctContainsVertex(vertex)) {	// Avoid adding primary vertices to the adjunct vertexMapDirected
                edges.addAll(super.outgoingEdgesOf(vertex));
            }
            else {
                edges.addAll(new LinkedHashSet<E>(getPrimaryEdgeContainer(vertex).outgoing));
            }
            return edges;
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.DirectedSpecifics#removeEdgeFromTouchingVertices(java.lang.Object)
         */
        @Override public void removeEdgeFromTouchingVertices(E e)
        {

            if (adjunctContainsEdge(e)) {
                super.removeEdgeFromTouchingVertices(e);
            }
            else {
                V source = getEdgeSource(e);
                V target = getEdgeTarget(e);
                getNegativeEdgeContainer(source).addOutgoingEdge(e);
                getNegativeEdgeContainer(target).addIncomingEdge(e);
                negativeEdges.add(e);
            }

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
            assertVertexExist(vertex);
            DirectedEdgeContainer<V, E> ec = negativeVertexMapDirected.get(vertex);
            if (ec == null) {
                ec = new DirectedEdgeContainer<V, E>(edgeSetFactory, vertex);
                negativeVertexMapDirected.put(vertex, ec);
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
            assertVertexExist(vertex);
            DirectedEdgeContainer<V, E> ec = primaryVertexMapDirected.get(vertex);
            if (ec == null) {
                ec = new DirectedEdgeContainer<V, E>(edgeSetFactory, vertex);
                primaryVertexMapDirected.put(vertex, ec);
            }
            return ec;
        }

        @Override public void removePrimaryVertex(V v)
        {
            //if (!negativeVertexMapDirected.containsKey(v))
            negativeVertexMapDirected.put(v, null);
        }

    }


    /**
     * A specifics interface that adds support to retreive information about negative edges and vertices in the primary
     * graph.
     * @author Horacio Hoyos
     *
     * @param <V> The Vertex type
     * @param <E> The Edge type
     */
    private interface AdjunctSpecifics<V, E>
            extends Specifics<V, E>
    {


        /**
         * Removes the primary vertex.
         *
         * @param v the v
         */
        public void removePrimaryVertex(V v);

        /**
         * Gets the all negative edges.
         *
         * @param sourceVertex the source vertex
         * @param targetVertex the target vertex
         * @return the all negative edges
         */
        public Set<E> getAllNegativeEdges(V sourceVertex, V targetVertex);

        /**
         * Gets the negative edges.
         *
         * @return the negative vertex set
         */
        public Set<E> getNegativeEdgeSet();

        /**
         * Gets the negative vertex set.
         *
         * @return the negative vertex set
         */
        public Set<V> getNegativeVertexSet();

        /**
         * Negative degree of.
         *
         * @param vertex the vertex
         * @return the int
         */
        public int negativeDegreeOf(V vertex);

        /**
         * Negative edges of.
         *
         * @param vertex the vertex
         * @return the sets the
         */
        public Set<E> negativeEdgesOf(V vertex);

        /**
         * Negative incoming edges of.
         *
         * @param vertex the vertex
         * @return the sets the
         */
        public Set<E> negativeIncomingEdgesOf(V vertex);

        /**
         * Negative in degree of.
         *
         * @param vertex the vertex
         * @return the int
         */
        public int negativeInDegreeOf(V vertex);

        /**
         * Negative out degree of.
         *
         * @param vertex the vertex
         * @return the int
         */
        public int negativeOutDegreeOf(V vertex);

        /**
         * Negative outgoing edges of.
         *
         * @param vertex the vertex
         * @return the sets the
         */
        public Set<E> negativeOutgoingEdgesOf(V vertex);

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
        private Map<V, UndirectedEdgeContainer<V, E>> negativeVertexMapDirected;

        /** The primary vertex map directed. */
        private Map<V, UndirectedEdgeContainer<V, E>> primaryVertexMapDirected;

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
            this.negativeVertexMapDirected = negativeVertexMap;
            this.primaryVertexMapDirected = primaryVertexMap;
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.UndirectedSpecifics#addEdgeToTouchingVertices(java.lang.Object)
         */
        @Override public void addEdgeToTouchingVertices(E e)
        {
            V source = getEdgeSource(e);
            V target = getEdgeTarget(e);
            if (adjunctContainsVertex(source)) {
                getEdgeContainer(source).addEdge(e);
            }
            else {
                getPrimaryEdgeContainer(source).addEdge(e);
            }
            if (!source.equals(target)) {
                if (adjunctContainsVertex(target)) {
                    getEdgeContainer(target).addEdge(e);
                }
                else {
                    getPrimaryEdgeContainer(target).addEdge(e);
                }
            }
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
                if (allowingLoops) { // then we must count, and add loops twice
                    int degree = 0;
                    Set<E> edges = getPrimaryEdgeContainer(vertex).vertexEdges;
                    for (E e : edges) {
                        if (getEdgeSource(e).equals(getEdgeTarget(e))) {
                            degree += 2;
                        } else {
                            degree += 1;
                        }
                    }
                    return degree;
                }
                else {
                    return getPrimaryEdgeContainer(vertex).edgeCount();
                }
            }
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
                return getPrimaryEdgeContainer(vertex).getUnmodifiableVertexEdges();
            }
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.UndirectedSpecifics#getAllEdges(java.lang.Object, java.lang.Object)
         */
        @Override public Set<E> getAllEdges(V sourceVertex, V targetVertex)
        {
            Set<E> edges = super.getAllEdges(sourceVertex, targetVertex);
            if (edges != null) { // containsVertex(sourceVertex) && containsVertex(targetVertex))
                if (!adjunctContainsVertex(sourceVertex)) {
                    Iterator<E> iter = getPrimaryEdgeContainer(sourceVertex).vertexEdges.iterator();
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
                }
            }
            return edges;
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#getAllNegativeEdges(java.lang.Object, java.lang.Object)
         */
        @Override
        public Set<E> getAllNegativeEdges(V sourceVertex, V targetVertex) {

            if (adjunctContainsVertex(sourceVertex) && containsVertex(targetVertex)) {
                Set<E> edges = new ArrayUnenforcedSet<E>();
                Iterator<E> iter = getNegativeEdgeContainer(sourceVertex).vertexEdges.iterator();
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
            }
            return Collections.emptySet();
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
                if (containsVertex(sourceVertex)
                    && containsVertex(targetVertex))
                {
                    Iterator<E> iter = getPrimaryEdgeContainer(sourceVertex).vertexEdges.iterator();
                    while (iter.hasNext()) {
                        E e = iter.next();

                        boolean equal =
                            isEqualsStraightOrInverted(
                                sourceVertex,
                                targetVertex,
                                e);

                        if (equal) {
                            return e;
                        }
                    }
                }
                return null;
            }
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#getNegativeEdgeSet()
         */
        @Override public Set<E> getNegativeEdgeSet()
        {
            return negativeEdges;
        }


        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#getNegativeVertexSet()
         */
        @Override public Set<V> getNegativeVertexSet()
        {
            return negativeVertexMapDirected.keySet();
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#negativeDegreeOf(java.lang.Object)
         */
        @Override public int negativeDegreeOf(V vertex)
        {
            if (!adjunctContainsVertex(vertex)) {	// Avoid adding adjunct vertices to the adjunct negativeVertexMapDirected
                if (allowingLoops) { // then we must count, and add loops twice
                    int degree = 0;
                    Set<E> edges = getNegativeEdgeContainer(vertex).vertexEdges;
                    for (E e : edges) {
                        if (getEdgeSource(e).equals(getEdgeTarget(e))) {
                            degree += 2;
                        } else {
                            degree += 1;
                        }
                    }
                    return degree;
                }
                else {
                    return getNegativeEdgeContainer(vertex).edgeCount();
                }
            }
            return 0;
        }


        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#negativeEdgesOf(java.lang.Object)
         */
        @Override public Set<E> negativeEdgesOf(V vertex)
        {
            if (!adjunctContainsVertex(vertex)) {
                UndirectedEdgeContainer<V, E> ec = getNegativeEdgeContainer(vertex);
                if (ec != null) {
                    return ec.getUnmodifiableVertexEdges();
                }
            }
            return Collections.emptySet();
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#negativeIncomingEdgesOf(java.lang.Object)
         */
        @Override public Set<E> negativeIncomingEdgesOf(V vertex)
        {
            throw new UnsupportedOperationException(NOT_IN_UNDIRECTED_GRAPH);
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#negativeInDegreeOf(java.lang.Object)
         */
        @Override public int negativeInDegreeOf(V vertex)
        {
            throw new UnsupportedOperationException(NOT_IN_UNDIRECTED_GRAPH);
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#negativeOutDegreeOf(java.lang.Object)
         */
        @Override public int negativeOutDegreeOf(V vertex)
        {
            throw new UnsupportedOperationException(NOT_IN_UNDIRECTED_GRAPH);
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractAdjunctGraph.AdjunctSpecifics#negativeOutgoingEdgesOf(java.lang.Object)
         */
        @Override public Set<E> negativeOutgoingEdgesOf(V vertex)
        {
            throw new UnsupportedOperationException(NOT_IN_UNDIRECTED_GRAPH);
        }

        /* (non-Javadoc)
         * @see org.jgrapht.graph.AbstractBaseGraph.UndirectedSpecifics#removeEdgeFromTouchingVertices(java.lang.Object)
         */
        @Override public void removeEdgeFromTouchingVertices(E e)
        {
            if (adjunctContainsEdge(e)) {
                super.removeEdgeFromTouchingVertices(e);
            } else {
                V source = getEdgeSource(e);
                V target = getEdgeTarget(e);
                getNegativeEdgeContainer(source).addEdge(e);
                if (!source.equals(target)) {
                    getNegativeEdgeContainer(target).addEdge(e);
                }
                negativeEdges.add(e);
            }
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
            assertVertexExist(vertex);
            // We need to test if the key exists, as having a vertex in the map means it
            // is deleted, even if the ec is null
            if (negativeVertexMapDirected.containsKey(vertex)) {
                UndirectedEdgeContainer<V, E> ec = negativeVertexMapDirected.get(vertex);
                if (ec == null) {
                    ec = new UndirectedEdgeContainer<V, E>(edgeSetFactory, vertex);
                    negativeVertexMapDirected.put(vertex, ec);
                }
                return ec;
            }
            return null;

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
            assertVertexExist(vertex);
            UndirectedEdgeContainer<V, E> ec = primaryVertexMapDirected.get(vertex);
            if (ec == null) {
                ec = new UndirectedEdgeContainer<V, E>(edgeSetFactory, vertex);
                primaryVertexMapDirected.put(vertex, ec);
            }
            return ec;
        }

        @Override public void removePrimaryVertex(V v)
        {
            //if (!negativeVertexMapDirected.containsKey(v))
            negativeVertexMapDirected.put(v, null);
        }


    }


}
