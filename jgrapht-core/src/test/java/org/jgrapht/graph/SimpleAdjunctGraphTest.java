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
        primary = new SimpleGraph<String, DefaultEdge>(
                DefaultEdge.class);
        primary.addVertex(v1);
        primary.addVertex(v2);
        primary.addEdge(v1, v2);
        primary.addEdge(v2, v1);
        adjunct = new SimpleAdjunctGraph<String, DefaultEdge>(primary);

    }

//	protected void tearDown() throws Exception {
//	}

    @Test
    public void testAccessBase() {

        assertTrue(adjunct.containsVertex(v1));
        assertTrue(adjunct.containsVertex(v2));
        DefaultEdge e1 = adjunct.getEdge(v1, v2);
        assertNotNull(e1);
        DefaultEdge e2 = adjunct.getEdge(v2, v1);
        assertNotNull(e2);
    }

    @Test
    public void testProtectedBase() {

        adjunct.removeVertex(v1);
        assertTrue(adjunct.containsVertex(v1));
        DefaultEdge e1 = adjunct.getEdge(v1, v2);
        assertNotNull(e1);
        adjunct.removeEdge(e1);
        assertTrue(adjunct.containsEdge(e1));
        adjunct.removeEdge(v1, v2);
        assertTrue(adjunct.containsEdge(e1));
    }

    @Test
    public void testJointGraph() {

        adjunct.addVertex(v3);
        assertTrue(adjunct.containsVertex(v3));
        adjunct.addVertex(v4);
        DefaultEdge e1 = adjunct.addEdge(v3, v4);
        assertTrue(adjunct.containsEdge(e1));
        DefaultEdge e2 = adjunct.addEdge(v1, v3);
        assertTrue(adjunct.containsEdge(e2));
        assertTrue(adjunct.edgeSet().contains(e1));
        assertTrue(adjunct.edgeSet().contains(e2));
        assertTrue(adjunct.vertexSet().contains(v2));
        assertTrue(adjunct.vertexSet().contains(v4));
    }

    @Test
    public void testMultiAdjunct() {

        adjunct.addVertex(v3);
        assertTrue(adjunct.containsVertex(v3));
        adjunct.addVertex(v4);
        assertTrue(adjunct.containsVertex(v4));
        DefaultEdge e1 = adjunct.addEdge(v3, v4);
        assertTrue(adjunct.containsEdge(e1));
        DefaultEdge e2 = adjunct.addEdge(v1, v3);
        assertTrue(adjunct.containsEdge(e2));
        SimpleAdjunctGraph<String, DefaultEdge> adjunct2 = new SimpleAdjunctGraph<String, DefaultEdge>(adjunct);
        adjunct2.addVertex(v5);
        assertTrue(adjunct2.containsVertex(v5));
        adjunct2.addVertex(v6);
        assertTrue(adjunct2.vertexSet().contains(v6));
        assertTrue(adjunct2.vertexSet().contains(v1));
        assertTrue(adjunct2.vertexSet().contains(v3));
        DefaultEdge e3 = adjunct2.addEdge(v5, v6);
        assertTrue(adjunct2.containsEdge(e3));
        DefaultEdge e4 = adjunct2.addEdge(v1, v6);
        assertTrue(adjunct2.containsEdge(e4));
        DefaultEdge e5 = adjunct2.addEdge(v3, v6);
        assertTrue(adjunct2.containsEdge(e5));

        assertTrue(adjunct.edgeSet().contains(e1));
        assertTrue(adjunct.edgeSet().contains(e2));
        assertTrue(adjunct.vertexSet().contains(v2));
        assertTrue(adjunct.vertexSet().contains(v4));
    }

    @Test
    public void testAdjunctGraph() {

        assertFalse(adjunct.adjunctContainsVertex(v1));
        assertFalse(adjunct.adjunctVertexSet().contains(v1));
        adjunct.addVertex(v3);
        assertTrue(adjunct.adjunctContainsVertex(v3));
        assertTrue(adjunct.adjunctVertexSet().contains(v3));
        adjunct.addVertex(v4);
        DefaultEdge e1 = adjunct.getEdge(v1, v2);
        assertFalse(adjunct.adjunctContainsEdge(e1));
        DefaultEdge e2 = adjunct.addEdge(v3, v4);
        assertTrue(adjunct.adjunctContainsEdge(e2));
        assertFalse(adjunct.adjunctEdgeSet().contains(e1));
        assertTrue(adjunct.adjunctEdgeSet().contains(e2));
    }

    @Test
    public void testDeleteBase() {

        DefaultEdge e1 = adjunct.getEdge(v1, v2);
        assertFalse(adjunct.adjunctContainsEdge(e1));
        // Remove primary edge
        adjunct.removeEdge(v1, v2);
        assertFalse(adjunct.adjunctContainsEdge(e1));
    }




}
