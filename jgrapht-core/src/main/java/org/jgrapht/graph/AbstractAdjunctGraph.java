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

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.jgrapht.AdjunctGraph;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.util.ArrayUnenforcedSet;

/**
 * The most general implementation of the {@link org.jgrapht.AdjunctGraph}
 * interface. Its subclasses add various restrictions to use the adjunct
 * interface with more specific graphs.
 *
 * @author Horacio Hoyos
 * @since Nov 28, 2015
 */
public abstract class AbstractAdjunctGraph<V, E>
    extends AbstractBaseGraph<V, E>
    implements AdjunctGraph<V, E>
{

    /**
     *
     */
    private static final long serialVersionUID = 5730647889266263861L;
    protected UnmodifiableGraph<V,E> primaryGraph;
    private PrimaryMask mask = new PrimaryMask();
    //private boolean jointAccess = true;

    public AbstractAdjunctGraph(Graph<V, E> primaryGraph, boolean allowMultipleEdges, boolean allowLoops) {
        super(primaryGraph.getEdgeFactory(), allowMultipleEdges, allowLoops);
        this.primaryGraph = new UnmodifiableGraph<V, E>(primaryGraph);

    }


    @Override public Set<E> getAllEdges(V sourceVertex, V targetVertex) {

        Set<E> edges = super.getAllEdges(sourceVertex, targetVertex);
        edges.addAll(primaryGraph.getAllEdges(sourceVertex, targetVertex));
        edges.removeAll(mask.getMaskedEdges());
        return edges;
    }

    @Override public E getEdge(V sourceVertex, V targetVertex) {

        E baseEdge  = super.getEdge(sourceVertex, targetVertex);
        if (baseEdge == null) {
            baseEdge = primaryGraph.getEdge(sourceVertex, targetVertex);
            if (mask.isEdgeMasked(baseEdge)) {
                return null;
            }
        }
        return baseEdge;
    }

    @Override public E addEdge(V sourceVertex, V targetVertex) {

        if (primaryGraph.containsVertex(sourceVertex) && primaryGraph.containsVertex(targetVertex)) {
            throw new UnsupportedOperationException("the base graph is unmodifiable");
        }
        return super.addEdge(sourceVertex, targetVertex);
    }

    @Override public boolean addEdge(V sourceVertex, V targetVertex, E e) {

        if (primaryGraph.containsVertex(sourceVertex) && primaryGraph.containsVertex(targetVertex)) {
            throw new UnsupportedOperationException("the base graph is unmodifiable");
        }
        return super.addEdge(sourceVertex, targetVertex, e);
    }

    @Override public V getEdgeSource(E e) {
        V source = null;
        if (adjunctContainsEdge(e)) {
            source = super.getEdgeSource(e);
        } else {
            source = primaryGraph.getEdgeSource(e);
        }
        return source;
    }

    @Override public V getEdgeTarget(E e) {
        V target = null;
        if (adjunctContainsEdge(e)) {
            target = super.getEdgeTarget(e);
        } else {
            target = primaryGraph.getEdgeTarget(e);
        }
        return target;
    }

    @Override public boolean containsEdge(E e) {

        if (mask.isEdgeMasked(e))
            return false;
        boolean contains = super.containsEdge(e);
        if (!contains /*&& jointAccess*/)
            contains = primaryGraph.containsEdge(e);
        return contains;
    }

    @Override public boolean containsEdge(V sourceVertex, V targetVertex) {

        // This delegates to getEdge which has the mask test
        return super.containsEdge(sourceVertex, targetVertex);
    }

    @Override public boolean containsVertex(V v) {

        if (mask.isVertexMasked(v))
            return false;
        boolean contains = super.containsVertex(v);
        if (!contains /*&& jointAccess*/)
            contains = primaryGraph.containsVertex(v);
        return contains;
    }

    @Override protected boolean assertVertexExist(V v) {

        if (containsVertex(v)) {
            return true;
        } /*else if (primaryGraph.containsVertex(v)){
            return true;
        } */else if (v == null) {
            throw new NullPointerException();
        } else {
            throw new IllegalArgumentException(
                "no such vertex in graph: " + v.toString());
        }
    }

    @Override public Graph<V, E> getPrimaryGraph() {
        return primaryGraph;
    }

    @Override public Set<E> edgeSet() {

        Set<E> edges = new LinkedHashSet<E>(super.edgeSet());
        edges.addAll(primaryGraph.edgeSet());
        edges.removeAll(mask.getMaskedEdges());
        return Collections.unmodifiableSet(edges);
    }

    @Override public Set<E> edgesOf(V vertex) {

        Set<E> edges = new LinkedHashSet<E>(super.edgesOf(vertex));
        edges.addAll(primaryGraph.edgeSet());
        edges.removeAll(mask.getMaskedEdges());
        return Collections.unmodifiableSet(edges);
    }

    @Override public E removeEdge(V sourceVertex, V targetVertex)
    {
        E e  = super.getEdge(sourceVertex, targetVertex);
        if (e == null) {
            e = primaryGraph.getEdge(sourceVertex, targetVertex);
            if (e != null) {
                mask.addMaskedEdge(e);
            }
        } else {
            super.removeEdge(e);
        }
        return e;
    }

    @Override public boolean removeEdge(E e)
    {
        boolean success = false;
        if (adjunctContainsEdge(e)) {
            success = super.removeEdge(e);
        }
        else {
            success = primaryGraph.containsEdge(e);
            if (success) {
                mask.addMaskedEdge(e);
            }
        }
        return success;
    }

    @Override public boolean removeVertex(V v)
    {

        boolean success = false;
        if (adjunctContainsVertex(v)) {
            success = super.removeVertex(v);
        }
        else {
            success = primaryGraph.containsVertex(v);
            if (success) {
                mask.addMaskedVertex(v);
            }
        }
        return success;
    }

    @Override public void setEdgeWeight(E e, double weight)
    {
        if (super.containsEdge(e)) {
            assert (e instanceof DefaultWeightedEdge) : e.getClass();
            ((DefaultWeightedEdge) e).weight = weight;
        } else {
            throw new UnsupportedOperationException("the base graph is unmodifiable");
        }
    }

    @Override public Set<V> vertexSet() {

        Set<V> vertices = new LinkedHashSet<V>(super.vertexSet());
        vertices.addAll(primaryGraph.vertexSet());
        vertices.removeAll(mask.getMaskedVertices());
        return Collections.unmodifiableSet(vertices);
    }

    @Override public boolean adjunctContainsEdge(V sourceVertex, V targetVertex) {

        return super.containsEdge(sourceVertex, targetVertex);
    }

    @Override public boolean adjunctContainsEdge(E e) {

        return super.containsEdge(e);
    }

    @Override public boolean adjunctContainsVertex(V v) {

        return super.containsVertex(v);
    }

    @Override public Set<E> adjunctEdgeSet() {
        return super.edgeSet();
    }

    @Override public Set<E> adjunctEdgesOf(V vertex) {
        return super.edgeSet();
    }

    @Override public Set<V> adjunctVertexSet() {
        return super.vertexSet();
    }

//    @Override protected UndirectedSpecifics createUndirectedSpecifics()
//    {
//        return new AdjunctUndirectedSpecifics();
//    }
//
//    @Override protected DirectedSpecifics createDirectedSpecifics()
//    {
//        return new AdjunctDirectedSpecifics();
//    }

    /**
     * A MaskFunctor to mask vertices and edges deleted from the primary graph
     * @author Horacio Hoyos
     *
     */
    private class PrimaryMask
    {

        private Set<E> maskedEdges;
        private Set<V> maskedVertices;

        PrimaryMask()
        {
            this.maskedEdges = new HashSet<E>();
            this.maskedVertices = new HashSet<V>();
        }

        public void addMaskedVertex(V v) {
            maskedVertices.add(v);
        }

        public void addMaskedEdge(E e) {
            maskedEdges.add(e);
        }

        public boolean isEdgeMasked(E edge) {

            return maskedEdges.contains(edge);
        }

        public boolean isVertexMasked(V vertex) {

            return maskedVertices.contains(vertex);
        }

        /**
         * @return the maskedEdges
         */
        public Set<E> getMaskedEdges() {
            return maskedEdges;
        }

        /**
         * @return the maskedVertices
         */
        public Set<V> getMaskedVertices() {
            return maskedVertices;
        }

    }

    /**
     * An adjunct graph specifics keeps track of deleted edges and nodes in the primary graph so that it can have a view
     * of a modified primary graph without modifying it.
     *
     * @author Horacio Hoyos
     */
    private class AdjunctDirectedSpecifics
        extends DirectedSpecifics
        implements Serializable
    {

        /**
         *
         */
        private static final long serialVersionUID = 5654243876545966826L;
        private Set<E> negativeEdges = new HashSet<E>();
        private Map<V, DirectedEdgeContainer<V, E>> negativeVertexMapDirected;

        public AdjunctDirectedSpecifics()
        {
            this(new LinkedHashMap<V, DirectedEdgeContainer<V, E>>());
        }

        public AdjunctDirectedSpecifics(
                Map<V, DirectedEdgeContainer<V, E>> negativeVertexMap)
        {
            super();
            this.negativeVertexMapDirected = negativeVertexMap;
        }

        public AdjunctDirectedSpecifics(
            Map<V, DirectedEdgeContainer<V, E>> vertexMap,
            Map<V, DirectedEdgeContainer<V, E>> negativeVertexMap)
        {
            super(vertexMap);
            this.negativeVertexMapDirected = negativeVertexMap;
        }

        @Override public Set<V> getVertexSet()
        {
            Set<V> vertexView = new LinkedHashSet<V>(super.getVertexSet());
            vertexView.removeAll(negativeVertexMapDirected.keySet());
            return vertexView;
        }

        /**
         * @see Graph#getAllEdges(Object, Object)
         */
        @Override public Set<E> getAllEdges(V sourceVertex, V targetVertex)
        {
            Set<E> edges = super.getAllEdges(sourceVertex, targetVertex);

            if (containsVertex(sourceVertex)
                && containsVertex(targetVertex))
            {
                Set<E> negEdges = new ArrayUnenforcedSet<E>();

                Iterator<E> iter = getNegativeEdgeContainer(sourceVertex).outgoing.iterator();
                while (iter.hasNext()) {
                    E e = iter.next();

                    if (getEdgeTarget(e).equals(targetVertex)) {
                        negEdges.add(e);
                    }
                }
                edges.removeAll(negEdges);
            }
            return edges;
        }

        /**
         * @see Graph#getEdge(Object, Object)
         */
        @Override public E getEdge(V sourceVertex, V targetVertex)
        {

            // See if the edge has been deleted
            if (containsVertex(sourceVertex)
                && containsVertex(targetVertex))
            {
                Iterator<E> iter = getNegativeEdgeContainer(sourceVertex).outgoing.iterator();
                while (iter.hasNext()) {
                    E e = iter.next();
                    if (getEdgeTarget(e).equals(targetVertex)) {
                        return null;	// If deleted, return null
                    }
                }
            }
            // If not, get it
            return super.getEdge(sourceVertex, targetVertex);
        }

        /**
         * @see Graph#edgesOf(Object)
         */
        @Override public Set<E> edgesOf(V vertex)
        {

            ArrayUnenforcedSet<E> existing = new ArrayUnenforcedSet<E>(super.edgesOf(vertex));
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
            // Remove the deleted ones
            existing.removeAll(inAndOut);
            return Collections.unmodifiableSet(existing);
        }

        /**
         * @see DirectedGraph#inDegreeOf(Object)
         */
        @Override public int inDegreeOf(V vertex)
        {
            int existing = super.degreeOf(vertex);
            return existing - getNegativeEdgeContainer(vertex).incoming.size();
        }

        /**
         * @see DirectedGraph#incomingEdgesOf(Object)
         */
        @Override public Set<E> incomingEdgesOf(V vertex)
        {
            Set<E> existing = new LinkedHashSet<E>(incomingEdgesOf(vertex));
            existing.removeAll(getNegativeEdgeContainer(vertex).getUnmodifiableIncomingEdges());
            return existing;
        }

        /**
         * @see DirectedGraph#outDegreeOf(Object)
         */
        @Override public int outDegreeOf(V vertex)
        {

            int existing = super.outDegreeOf(vertex);
            return existing - getNegativeEdgeContainer(vertex).outgoing.size();
        }

        /**
         * @see DirectedGraph#outgoingEdgesOf(Object)
         */
        @Override public Set<E> outgoingEdgesOf(V vertex)
        {
            Set<E> existing = new LinkedHashSet<E>(outgoingEdgesOf(vertex));
            existing.removeAll(getNegativeEdgeContainer(vertex).getUnmodifiableOutgoingEdges());
            return existing;
        }

        @Override public void removeEdgeFromTouchingVertices(E e)
        {

            if (adjunctContainsEdge(e)) {
                super.removeEdgeFromTouchingVertices(e);
            } else {
                V source = getEdgeSource(e);
                V target = getEdgeTarget(e);
                getNegativeEdgeContainer(source).addOutgoingEdge(e);
                getNegativeEdgeContainer(target).addIncomingEdge(e);
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

    }


    /**
     * An adjunct graph specifics keeps track of deleted edges and nodes in the primary graph so that it can have a view
     * of a modified primary graph without modifying it.
     *
     * @author Horacio Hoyos
     */
    private class AdjunctUndirectedSpecifics
        extends UndirectedSpecifics
        implements Serializable
    {

        /**
         *
         */
        private static final long serialVersionUID = 3841392502468479003L;
        private Set<E> negativeEdges = new HashSet<E>();
        private Set<E> negativeNodes = new HashSet<E>();
        private Map<V, UndirectedEdgeContainer<V, E>> negativeVertexMapDirected;

        public AdjunctUndirectedSpecifics()
        {
            this(new LinkedHashMap<V, UndirectedEdgeContainer<V, E>>());
        }

        public AdjunctUndirectedSpecifics(
                Map<V, UndirectedEdgeContainer<V, E>> negativeVertexMap)
        {
            super();
            this.negativeVertexMapDirected = negativeVertexMap;
        }

        public AdjunctUndirectedSpecifics(
            Map<V, UndirectedEdgeContainer<V, E>> vertexMap,
            Map<V, UndirectedEdgeContainer<V, E>> negativeVertexMap)
        {
            super(vertexMap);
            this.negativeVertexMapDirected = negativeVertexMap;
        }


        @Override public Set<V> getVertexSet()
        {
            Set<V> vertexView = new LinkedHashSet<V>(super.getVertexSet());
            Set<V> deleted = new HashSet<V>();
            vertexView.removeAll(
                    negativeVertexMapDirected.keySet());
            return vertexView;
        }

        /**
         * @see Graph#getAllEdges(Object, Object)
         */
        @Override public Set<E> getAllEdges(V sourceVertex, V targetVertex)
        {
            Set<E> edges = super.getAllEdges(sourceVertex, targetVertex);

            if (containsVertex(sourceVertex)
                && containsVertex(targetVertex))
            {
                Set<E> negEdges = new ArrayUnenforcedSet<E>();

                Iterator<E> iter = getNegativeEdgeContainer(sourceVertex).vertexEdges.iterator();
                while (iter.hasNext()) {
                    E e = iter.next();

                    if (getEdgeTarget(e).equals(targetVertex)) {
                        negEdges.add(e);
                    }
                }
                edges.removeAll(negEdges);
            }
            return edges;
        }

        /**
         * @see Graph#getEdge(Object, Object)
         */
        @Override public E getEdge(V sourceVertex, V targetVertex)
        {
            // See if the edge has been deleted
            if (containsVertex(sourceVertex)
                && containsVertex(targetVertex))
            {
                Iterator<E> iter = getNegativeEdgeContainer(sourceVertex).vertexEdges.iterator();
                while (iter.hasNext()) {
                    E e = iter.next();
                    if (getEdgeTarget(e).equals(targetVertex)) {
                        return null;	// If deleted, return null
                    }
                }
            }
            // If not, get it
            return super.getEdge(sourceVertex, targetVertex);
        }

        @Override public int degreeOf(V vertex)
        {

            int existing = super.degreeOf(vertex);
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

                return existing - degree;
            } else {
                return existing - getNegativeEdgeContainer(vertex).edgeCount();
            }
        }

        /**
         * @see Graph#edgesOf(Object)
         */
        @Override public Set<E> edgesOf(V vertex)
        {
            Set<E> existing = new LinkedHashSet<E>(super.edgesOf(vertex));
            existing.removeAll(getNegativeEdgeContainer(vertex).getUnmodifiableVertexEdges());
            return existing;
        }

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
            }
        }

//        private boolean isEqualsStraightOrInverted(
//                Object sourceVertex,
//                Object targetVertex,
//                E e)
//            {
//                boolean equalStraight =
//                    sourceVertex.equals(getEdgeSource(e))
//                    && targetVertex.equals(getEdgeTarget(e));
//
//                boolean equalInverted =
//                    sourceVertex.equals(getEdgeTarget(e))
//                    && targetVertex.equals(getEdgeSource(e));
//                return equalStraight || equalInverted;
//            }

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
            UndirectedEdgeContainer<V, E> ec = negativeVertexMapDirected.get(vertex);
            if (ec == null) {
                ec = new UndirectedEdgeContainer<V, E>(edgeSetFactory, vertex);
                negativeVertexMapDirected.put(vertex, ec);
            }
            return ec;
        }
    }

}
