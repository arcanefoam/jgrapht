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
package org.jgrapht;

/**
 * A graph that extends another graph, but is not essential to it (i.e. it does
 * not change the structure of the extended graph). 
 * 
 * All graph operations will consider the adjunct graph and the source graph as
 * a single graph, except the remove operations which are restricted to removing
 * elements of the adjunct graph. 
 *
 * @param <V> the value type
 * @param <E> the element type
 */
public interface AdjuntGraph<V, E> extends Graph<V, E> {
	
	/**
	 * Returns the graph that is the base for this graph. The source graph is 
	 * wrapped in an UnmodifiableGraph to guarantee that it can not be modified
	 * while accessing the adjunct graph. 
	 * @return
	 */
	public Graph<V, E> getBaseGraph();

}
