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
 * 05-Jan-2016 : Additional tests (HH);
 *
 */
package org.jgrapht.graph;

import org.jgrapht.*;

import org.junit.Test;
import junit.framework.TestCase;

/**
 * Unit test for {@link SimpleAdjunctGraph} class.
 */
public class SimpleAdjunctGraphTest
    extends TestCase
{
    private String v1 = "v1";
    private String v2 = "v2";
    private String v3 = "v3";
    private String v4 = "v4";
    private String v5 = "v5";
    private String v6 = "v6";
    private String v7 = "v7";

    private SimpleGraph<String, DefaultEdge> primary;
    private SimpleAdjunctGraph<String, DefaultEdge> adjunct;

    /**
     * @see junit.framework.TestCase#TestCase(java.lang.String)
     */
    public SimpleAdjunctGraphTest(String name)
    {
        super(name);
    }

    @Override
    protected void setUp() throws Exception
    {
        // Construct a graph
        //	V1_____ V2
        //	  \____ V3____V4
        primary = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
        primary.addVertex(v1);
        primary.addVertex(v2);
        primary.addVertex(v3);
        primary.addVertex(v4);
        primary.addEdge(v1, v2);
        primary.addEdge(v1, v3);
        primary.addEdge(v3, v4);
        adjunct = new SimpleAdjunctGraph<String, DefaultEdge>(primary);
    }

//	protected void tearDown() throws Exception {
//	}

    @Test
    public void testAccessPrimary() {

        assertTrue(adjunct.containsVertex(v1));
        assertTrue(adjunct.containsVertex(v2));
        assertTrue(adjunct.containsVertex(v3));
        assertTrue(adjunct.containsVertex(v4));
        DefaultEdge e12 = adjunct.getEdge(v1, v2);
        assertNotNull(e12);
        assertTrue(adjunct.containsEdge(e12));
        DefaultEdge  e13 = adjunct.getEdge(v1, v3);
        assertNotNull(e13);
        assertTrue(adjunct.containsEdge(e13));
        DefaultEdge e34 = adjunct.getEdge(v3, v4);
        assertNotNull(e34);
        assertTrue(adjunct.containsEdge(e34));
        assertTrue(adjunct.edgesOf(v1).contains(e12));
        assertTrue(adjunct.edgesOf(v1).contains(e13));
        assertTrue(adjunct.edgesOf(v3).contains(e34));
        assertTrue(adjunct.edgesOf(v3).contains(e13));
        assertTrue(adjunct.edgesOf(v4).contains(e34));
    }

    @Test
    public void testAdjunctGraph() {

         // Construct a graph
        //	V1_____V2
        //	  \      \____V5____V6
        //     \     |
        //      \____V3____V4
        //             \
        //              \____V7
        adjunct.addVertex(v5);
        assertTrue(adjunct.containsVertex(v5));
        adjunct.addVertex(v6);
        assertTrue(adjunct.containsVertex(v6));
        adjunct.addVertex(v7);
        assertTrue(adjunct.containsVertex(v7));
        DefaultEdge e = adjunct.addEdge(v2, v5);
        assertTrue(adjunct.containsEdge(e));
        assertFalse(primary.containsEdge(e));
        e = adjunct.addEdge(v5, v6);
        assertTrue(adjunct.containsEdge(e));
        e = adjunct.addEdge(v2, v3);
        assertTrue(adjunct.containsEdge(e));
        assertFalse(primary.containsEdge(e));
        e = adjunct.addEdge(v3, v7);
        assertTrue(adjunct.containsEdge(e));
    }

    @Test
    public void testModifyAdjunctGraph() {

        // Construct a graph
        //	V1_____V2
        //	  \      \____V5____V6
        //     \     |
        //      \____V3____V4
        //             \
        //              \____V7
        adjunct.addVertex(v5);
        adjunct.addVertex(v6);
        adjunct.addVertex(v7);
        adjunct.addEdge(v2, v5);
        DefaultEdge e56 = adjunct.addEdge(v5, v6);
        adjunct.addEdge(v2, v3);
        adjunct.addEdge(v3, v7);
        // Transform too
        //	V1_____V2____V6
        //	  \      \____V5
        //     \     |
        //      \____V3____V4
        adjunct.removeEdge(v5, v6);
        assertFalse(adjunct.containsEdge(e56));
        assertFalse(adjunct.edgesOf(v5).contains(v6));
        adjunct.addEdge(v2, v6);
        assertTrue(adjunct.edgesOf(v2).contains(v6));
        assertFalse(primary.edgesOf(v2).contains(v6));
        adjunct.removeVertex(v7);
        assertFalse(adjunct.edgesOf(v3).contains(v7));
        assertTrue(adjunct.edgesOf(v3).contains(v4));
        assertTrue(adjunct.edgesOf(v3).contains(v2));
    }



}
