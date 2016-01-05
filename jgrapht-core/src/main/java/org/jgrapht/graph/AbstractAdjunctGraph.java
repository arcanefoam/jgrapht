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

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.AdjunctGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;

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
	protected UnmodifiableGraph<V,E> base;
	private boolean jointAccess = true;

	public AbstractAdjunctGraph(EdgeFactory<V, E> ef, boolean allowMultipleEdges, boolean allowLoops) {
		super(ef, allowMultipleEdges, allowLoops);
	}

	@Override
	public Set<E> getAllEdges(V sourceVertex, V targetVertex) {
		
		Set<E> baseEdges = new HashSet<E>(base.getAllEdges(sourceVertex, targetVertex));
		if (jointAccess)
			baseEdges.addAll(super.getAllEdges(sourceVertex, targetVertex));
		return baseEdges; 
	}

	@Override
	public E getEdge(V sourceVertex, V targetVertex) {
		
		E baseEdge  = super.getEdge(sourceVertex, targetVertex);
		if ((baseEdge == null) && jointAccess) {
			baseEdge = base.getEdge(sourceVertex, targetVertex);
		}
		return baseEdge;
	}

	@Override
	public E addEdge(V sourceVertex, V targetVertex) {
		
		if (base.containsVertex(sourceVertex) && base.containsVertex(targetVertex)) {
			throw new UnsupportedOperationException("the base graph is unmodifiable");
		}
		return super.addEdge(sourceVertex, targetVertex);
	}

	@Override
	public boolean addEdge(V sourceVertex, V targetVertex, E e) {
		
		if (base.containsVertex(sourceVertex) && base.containsVertex(targetVertex)) {
			throw new UnsupportedOperationException("the base graph is unmodifiable");
		}
		return super.addEdge(sourceVertex, targetVertex, e);
	}

	@Override
	public V getEdgeSource(E e) {
		
		V source = super.getEdgeSource(e);
		if (source == null)
			source = base.getEdgeSource(e);
		return source;
	}

	@Override
	public V getEdgeTarget(E e) {
		V target = super.getEdgeTarget(e);
		if (target == null)
			target = base.getEdgeTarget(e);
		return target;
	}

	@Override
	public boolean containsEdge(E e) {
		boolean contains = super.containsEdge(e);
		if (!contains && jointAccess)
			contains = base.containsEdge(e);
		return contains;
	}

	@Override
	public boolean containsEdge(V sourceVertex, V targetVertex) {
		boolean contains = super.containsEdge(sourceVertex, targetVertex);
		if (!contains && jointAccess)
			contains = base.containsEdge(sourceVertex, targetVertex);
		return contains;
	}

	@Override
	public boolean containsVertex(V v) {
		
		boolean contains = super.containsVertex(v);
		if (!contains && jointAccess)
			contains = base.containsVertex(v);
		return contains;
	}

	@Override
	protected boolean assertVertexExist(V v) {
		
		if (containsVertex(v)) {
	        return true;
	    } else if (base.containsVertex(v)){
	    	return true;
	    } else if (v == null) {
	        throw new NullPointerException();
	    } else {
	        throw new IllegalArgumentException(
	            "no such vertex in graph: " + v.toString());
	    }
	}

	@Override
	public Graph<V, E> getBaseGraph() {
		return base;
	}

	@Override
	public Set<E> edgeSet() {
	
		Set<E> edges = new HashSet<E>(super.edgeSet());
		if (jointAccess)
			edges.addAll(base.edgeSet());
		return edges;
	}

	@Override
	public Set<E> edgesOf(V vertex) {
		
		Set<E> edges;
		edges = super.edgesOf(vertex);
		if (jointAccess)
			edges.addAll(base.edgeSet());
		edges.addAll(base.edgesOf(vertex));
		return edges;
	}
	
	@Override
	public E removeEdge(V sourceVertex, V targetVertex)
    {
		jointAccess = false;
		E e = super.removeEdge(sourceVertex, targetVertex);
        jointAccess = true;
        return e;
    }

    @Override
    public boolean removeEdge(E e)
    {
    	jointAccess = false;
        boolean success = super.removeEdge(e);
        jointAccess = true;
        return success;
    }
	
	@Override
	public boolean removeVertex(V v)
    {
		jointAccess = false;
        boolean success = super.removeVertex(v);
        jointAccess = true;
        return success;
    }
	
	@Override
    public void setEdgeWeight(E e, double weight)
    {
		if (super.containsEdge(e)) {
			assert (e instanceof DefaultWeightedEdge) : e.getClass();
	        ((DefaultWeightedEdge) e).weight = weight;
		} else {
			throw new UnsupportedOperationException("the base graph is unmodifiable");
		}
    }

	@Override
	public Set<V> vertexSet() {
		
		Set<V> vertices = new HashSet<V>(super.vertexSet());
		if (jointAccess)
			vertices.addAll(base.vertexSet());
		return vertices;
	}

	@Override
	public boolean adjunctContainsEdge(V sourceVertex, V targetVertex) {
		
		jointAccess = false;
        boolean success = containsEdge(sourceVertex, targetVertex);
        jointAccess = true;
        return success;
	}

	@Override
	public boolean adjunctContainsEdge(E e) {
		
		jointAccess = false;
        boolean success = containsEdge(e);
        jointAccess = true;
        return success;
	}

	@Override
	public boolean adjunctContainsVertex(V v) {
		jointAccess = false;
        boolean success = containsVertex(v);
        jointAccess = true;
        return success;
	}

	@Override
	public Set<E> adjunctEdgeSet() {
		jointAccess = false;
        Set<E> edges = edgeSet();
        jointAccess = true;
        return edges;
	}

	@Override
	public Set<E> adjunctEdgesOf(V vertex) {
		jointAccess = false;
        Set<E> edges = edgesOf(vertex);
        jointAccess = true;
        return edges;
	}

	@Override
	public Set<V> adjunctVertexSet() {
		jointAccess = false;
        Set<V> edges = vertexSet();
        jointAccess = true;
        return edges;
	}
	
	

}