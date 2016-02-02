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
        DefaultEdge  e13 = adjunct.getEdge(v1, v3);
        DefaultEdge e34 = adjunct.getEdge(v3, v4);
        assertNotNull(e12);
        assertNotNull(e13);
        assertNotNull(e34);
        assertTrue(adjunct.getEdgeSource(e12).equals(v1));
        assertTrue(adjunct.getEdgeTarget(e12).equals(v2));
        assertTrue(adjunct.containsEdge(e12));
        assertTrue(adjunct.getEdgeSource(e13).equals(v1));
        assertTrue(adjunct.getEdgeTarget(e13).equals(v3));
        assertTrue(adjunct.containsEdge(e13));
        assertTrue(adjunct.getEdgeSource(e34).equals(v3));
        assertTrue(adjunct.getEdgeTarget(e34).equals(v4));
        assertTrue(adjunct.containsEdge(e34));
        assertTrue(adjunct.edgesOf(v1).contains(e12));
        assertTrue(adjunct.edgesOf(v1).contains(e13));
        assertTrue(adjunct.edgesOf(v3).contains(e34));
        assertTrue(adjunct.edgesOf(v3).contains(e13));
        assertTrue(adjunct.edgesOf(v4).contains(e34));
        assertEquals(2, adjunct.degreeOf(v1));
        assertEquals(1, adjunct.degreeOf(v2));
        assertEquals(2, adjunct.degreeOf(v3));
        assertEquals(1, adjunct.degreeOf(v4));
        assertTrue(adjunct.edgeSet().contains(e12));
        assertTrue(adjunct.edgeSet().contains(e13));
        assertTrue(adjunct.edgeSet().contains(e34));

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
        adjunct.addVertex(v6);
        adjunct.addVertex(v7);
        DefaultEdge e25 = adjunct.addEdge(v2, v5);
        DefaultEdge e56 = adjunct.addEdge(v5, v6);
        DefaultEdge e23 = adjunct.addEdge(v2, v3);
        DefaultEdge e37 = adjunct.addEdge(v3, v7);

        assertTrue(adjunct.containsVertex(v5));
        assertTrue(adjunct.containsVertex(v6));
        assertTrue(adjunct.containsVertex(v7));
        assertTrue(adjunct.containsEdge(e25));
        assertFalse(primary.containsEdge(e25));
        assertTrue(adjunct.getEdgeSource(e25).equals(v2));
        assertTrue(adjunct.getEdgeTarget(e25).equals(v5));
        assertTrue(adjunct.containsEdge(e56));
        assertFalse(primary.containsEdge(e56));
        assertTrue(adjunct.getEdgeSource(e56).equals(v5));
        assertTrue(adjunct.getEdgeTarget(e56).equals(v6));
        assertTrue(adjunct.containsEdge(e23));
        assertFalse(primary.containsEdge(e23));
        assertTrue(adjunct.getEdgeSource(e23).equals(v2));
        assertTrue(adjunct.getEdgeTarget(e23).equals(v3));
        assertTrue(adjunct.containsEdge(e37));
        assertFalse(primary.containsEdge(e37));
        assertTrue(adjunct.getEdgeSource(e37).equals(v3));
        assertTrue(adjunct.getEdgeTarget(e37).equals(v7));
        assertEquals(2, adjunct.degreeOf(v1));
        assertEquals(3, adjunct.degreeOf(v2));
        assertEquals(4, adjunct.degreeOf(v3));
        assertEquals(1, adjunct.degreeOf(v4));
        assertEquals(2, adjunct.degreeOf(v5));
        assertEquals(1, adjunct.degreeOf(v6));
        assertEquals(1, adjunct.degreeOf(v7));
        assertTrue(adjunct.edgeSet().contains(e25));
        assertTrue(adjunct.edgeSet().contains(e56));
        assertTrue(adjunct.edgeSet().contains(e23));
        assertTrue(adjunct.edgeSet().contains(e37));
        assertTrue(adjunct.edgesOf(v2).contains(e25));
        assertTrue(adjunct.edgesOf(v5).contains(e25));
        assertTrue(adjunct.edgesOf(v2).contains(e23));
        assertTrue(adjunct.edgesOf(v3).contains(e23));
        assertTrue(adjunct.edgesOf(v5).contains(e56));
        assertTrue(adjunct.edgesOf(v6).contains(e56));
        assertTrue(adjunct.edgesOf(v3).contains(e37));
        assertTrue(adjunct.edgesOf(v7).contains(e37));
    }

    @Test
    public void testModifyGraph() {

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
        DefaultEdge e25 = adjunct.addEdge(v2, v5);
        DefaultEdge e56 = adjunct.addEdge(v5, v6);
        DefaultEdge e23 = adjunct.addEdge(v2, v3);
        DefaultEdge e37 = adjunct.addEdge(v3, v7);
        // Transform too
        //	V1_____V2____V6
        //	        \____V5
        //                \
        //      		  V3____V4___V7
        assertFalse(primary.containsEdge(e23));
        adjunct.removeEdge(v5, v6);
        assertFalse(adjunct.containsEdge(e56));
        assertFalse(adjunct.edgeSet().contains(e56));
        assertFalse(adjunct.edgesOf(v5).contains(e56));
        assertFalse(adjunct.edgesOf(v6).contains(e56));
        assertEquals(1, adjunct.degreeOf(v5));
        assertEquals(0, adjunct.degreeOf(v6));
        DefaultEdge e26 = adjunct.addEdge(v2, v6);
        assertTrue(adjunct.edgesOf(v2).contains(e26));
        assertFalse(primary.edgesOf(v2).contains(26));
        assertTrue(adjunct.getEdgeSource(e26).equals(v2));
        assertTrue(adjunct.getEdgeTarget(e26).equals(v6));
        DefaultEdge e13 = adjunct.removeEdge(v1, v3);
        assertFalse(adjunct.containsEdge(e13));
        assertTrue(primary.containsEdge(e13));
        assertFalse(adjunct.edgesOf(v1).contains(e13));
        assertFalse(adjunct.edgesOf(v3).contains(e13));
        DefaultEdge e23d = adjunct.removeEdge(v2, v3);
        assertEquals(e23, e23d);
        assertFalse(adjunct.containsEdge(e23));
        assertFalse(adjunct.edgesOf(v2).contains(e23));
        assertFalse(adjunct.edgesOf(v3).contains(e23));
        DefaultEdge e53 = adjunct.addEdge(v5, v3);
        DefaultEdge e37d = adjunct.removeEdge(v3, v7);
        assertEquals(e37, e37d);
        DefaultEdge e47 = adjunct.addEdge(v4, v7);
        DefaultEdge e12 = adjunct.edgesOf(v1).iterator().next();
        assertTrue(adjunct.edgesOf(v1).contains(e12));
        assertTrue(adjunct.edgesOf(v2).contains(e12));
        assertTrue(adjunct.edgesOf(v2).contains(e26));
        assertTrue(adjunct.edgesOf(v2).contains(e25));
        assertEquals(3, adjunct.degreeOf(v2));
        assertTrue(adjunct.edgesOf(v3).contains(e53));
        DefaultEdge e34 = adjunct.getEdge(v3, v4);
        assertTrue(adjunct.edgesOf(v3).contains(e34));
        assertTrue(adjunct.containsEdge(e47));
    }



}
