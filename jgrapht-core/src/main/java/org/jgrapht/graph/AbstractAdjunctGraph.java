package org.jgrapht.graph;

import java.util.Set;

import org.jgrapht.AdjunctGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;

public abstract class AbstractAdjunctGraph<V, E>
	extends AbstractBaseGraph<V, E>
	implements AdjunctGraph<V, E>
{

	protected UnmodifiableGraph<V,E> base;

	public AbstractAdjunctGraph(EdgeFactory<V, E> ef, boolean allowMultipleEdges, boolean allowLoops) {
		super(ef, allowMultipleEdges, allowLoops);
	}

	@Override
	public Set<E> getAllEdges(V sourceVertex, V targetVertex) {
		
		Set<E> baseEdges = base.getAllEdges(sourceVertex, targetVertex);
		baseEdges.addAll(super.getAllEdges(sourceVertex, targetVertex));
		return baseEdges; 
	}

	@Override
	public E getEdge(V sourceVertex, V targetVertex) {
		
		E baseEdge  = super.getEdge(sourceVertex, targetVertex);
		if (baseEdge == null) {
			baseEdge = base.getEdge(sourceVertex, targetVertex);
		}
		return baseEdge;
	}

	@Override
	public E addEdge(V sourceVertex, V targetVertex) {
		
		if (base.containsVertex(sourceVertex) && base.containsVertex(targetVertex)) {
			throw new IllegalArgumentException(
	                "Both vertices belong to the base graph. Can't modifiy the base graph.");
		}
		return super.addEdge(sourceVertex, targetVertex);
	}

	@Override
	public boolean addEdge(V sourceVertex, V targetVertex, E e) {
		
		if (base.containsVertex(sourceVertex) && base.containsVertex(targetVertex)) {
			throw new IllegalArgumentException(
	                "Both vertices belong to the base graph. Can't modifiy the base graph.");
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
		if (!contains)
			contains = base.containsEdge(e);
		return contains;
	}

	@Override
	public boolean containsEdge(V sourceVertex, V targetVertex) {
		boolean contains = super.containsEdge(sourceVertex, targetVertex);
		if (!contains)
			contains = base.containsEdge(sourceVertex, targetVertex);
		return contains;
	}

	@Override
	public boolean containsVertex(V v) {
		
		boolean contains =super.containsVertex(v);
		if (!contains)
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
		// TODO Auto-generated method stub
		return base;
	}

	@Override
	public Set<E> edgeSet() {
	
		Set<E> edges = super.edgeSet();
		edges.addAll(base.edgeSet());
		return edges;
	}

	@Override
	public Set<E> edgesOf(V vertex) {
		
		Set<E> edges;
		try {
			edges = super.edgesOf(vertex);
		} catch (IllegalArgumentException ex) {
			edges = base.edgesOf(vertex);
		}
		return edges;
	}

}