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

import org.jgrapht.AdjunctGraph;
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
    
    private SimpleGraph<String, DefaultEdge> base;
    private AdjunctGraph<String, DefaultEdge> adjunct;

    /**
     * @see junit.framework.TestCase#TestCase(java.lang.String)
     */
    public SimpleAdjunctGraphTest(String name)
    {
        super(name);
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
    
	@Override
	protected void setUp() throws Exception
    {
    	base = new SimpleGraph<String, DefaultEdge>(
                DefaultEdge.class);
    	base.addVertex(v1);
    	base.addVertex(v2);
    	base.addEdge(v1, v2);
    	base.addEdge(v2, v1);
    	adjunct = new SimpleAdjunctGraph<String, DefaultEdge>(base);
    	
    }
}
