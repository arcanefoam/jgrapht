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
package org.jgrapht;

// TODO: Auto-generated Javadoc
/**
 * The Interface SimoesGraphPath.
 *
 * @param <V> the value type
 * @param <E> the element type
 */
public interface SimoesGraphPath<V, E> extends GraphPath<V, E> {

    /**
     * Allows incremental construction of the path. The edge must extend the path, i.e. it´s source should be the last
     * known end vertex.
     *
     * @param edge the edge
     */
    public void addEdge(E edge);

    /**
     * Test if the path contains the vertex
     *
     * @param vertex the vertex
     * @return true, if successful
     */
    public boolean contains(V vertex);

    /**
     * If this path has a loop, it returns the vertex that is the start/end of the loop. Null other wise.
     *
     * @return the loop vertex
     */
    public V getLoopVertex();

    /**
     * True if the path has a loop.
     *
     * @return true, if successful
     */
    public boolean hasLoop();

    /**
     * Sets the loop vertex.
     *
     * @param loopVertex the new loop vertex
     */
    public void setLoopVertex(V loopVertex);

}
