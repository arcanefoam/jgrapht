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

import org.jgrapht.*;


/**
 * The Class SimpleDirectedAdjunctGraph is the simplest implementation of a directed adjunct graph.
 *
 * @param <V> the value type
 * @param <E> the element type
 */
public class SimpleDirectedAdjunctGraph<V, E>
    extends AbstractAdjunctGraph<V, E>
    implements DirectAdjunctGraph<V, E>
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5296261373996749227L;


    /**
     * Instantiates a new simple directed adjunct graph, with the same constraints as a {@link DefaultDirectedGraph}
     *
     * @param base the base
     */
    public SimpleDirectedAdjunctGraph(DirectedGraph<V, E> primaryGraph) {
        super(primaryGraph,  false, true);
    }




}
