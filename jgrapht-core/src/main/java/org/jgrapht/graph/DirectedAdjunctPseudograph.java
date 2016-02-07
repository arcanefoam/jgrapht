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
 * 03-Feb-2016 : Initial revision (HH);
 *
 */
package org.jgrapht.graph;

import org.jgrapht.DirectAdjunctGraph;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;

public class DirectedAdjunctPseudograph<V, E> extends AbstractAdjunctGraph<V, E>
        implements DirectAdjunctGraph<V, E> {


    private static final long serialVersionUID = 4300687180513804587L;

    public DirectedAdjunctPseudograph(Graph<V, E> primaryGraph) {
        super(primaryGraph, true, true);
    }

}
