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
 * StringNameProvider.java
 * ------------------
 * (C) Copyright 2005-2008, by Charles Fry and Contributors.
 *
 * Original Author:  Charles Fry
 *
 * $Id$
 *
 * Changes
 * -------
 * 13-Dec-2005 : Initial Version (CF);
 *
 */
package org.jgrapht.ext;

import org.jgrapht.event.*;


/**
 * Generates vertex names by invoking {@link #toString()} on them. This assumes
 * that the vertex's {@link #toString()} method returns a unique String
 * representation for each vertex.
 *
 * @author Charles Fry
 */
public class StringNameProvider<V>
    implements VertexNameProvider<V>
{
    public StringNameProvider()
    {
    }

    /**
     * Returns the String representation of the unique integer representing a
     * vertex.
     *
     * @param vertex the vertex to be named
     *
     * @return the name of
     *
     * @see GraphListener#edgeAdded(GraphEdgeChangeEvent)
     */
    @Override public String getVertexName(V vertex)
    {
        return vertex.toString();
    }
}

// End StringNameProvider.java
