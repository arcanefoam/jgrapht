package org.jgrapht.graph;

import java.util.Set;

import org.jgrapht.AdjunctGraph;
import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.junit.Test;

import junit.framework.TestCase;

public class SimpleDirectedAdjunctGraphTest
        extends TestCase {

    private String v1 = "v1";
    private String v2 = "v2";
    private String v3 = "v3";
    private String v4 = "v4";
    private String v5 = "v5";
    private String v6 = "v6";
    private String v7 = "v7";

    private DirectedGraph<String, DefaultEdge> primary;
    private SimpleDirectedAdjunctGraph<String, DefaultEdge> adjunct;


    public SimpleDirectedAdjunctGraphTest(String name) {
        super(name);
    }

    @Override
    protected void setUp() throws Exception
    {
        // Construct a graph
        //	V1_____ V2
        //	  \____ V3____V4
        primary = new SimpleDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        primary.addVertex(v1);
        primary.addVertex(v2);
        primary.addVertex(v3);
        primary.addVertex(v4);
        primary.addEdge(v1, v2);
        primary.addEdge(v1, v3);
        primary.addEdge(v3, v4);
        adjunct = new SimpleDirectedAdjunctGraph<String, DefaultEdge>(primary);

    }

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
        assertEquals(2, adjunct.outDegreeOf(v1));
        assertEquals(1, adjunct.inDegreeOf(v2));
        assertEquals(1, adjunct.outDegreeOf(v3));
        assertEquals(1, adjunct.inDegreeOf(v4));
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
        assertEquals(2, adjunct.outDegreeOf(v1));
        assertEquals(0, adjunct.inDegreeOf(v1));
        assertEquals(2, adjunct.outDegreeOf(v2));
        assertEquals(1, adjunct.inDegreeOf(v2));
        assertEquals(2, adjunct.outDegreeOf(v3));
        assertEquals(2, adjunct.inDegreeOf(v3));
        assertEquals(0, adjunct.outDegreeOf(v4));
        assertEquals(1, adjunct.inDegreeOf(v4));
        assertEquals(1, adjunct.outDegreeOf(v5));
        assertEquals(1, adjunct.inDegreeOf(v5));
        assertEquals(0, adjunct.outDegreeOf(v6));
        assertEquals(1, adjunct.inDegreeOf(v6));
        assertEquals(1, adjunct.inDegreeOf(v7));
        assertEquals(0, adjunct.outDegreeOf(v7));
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
        assertEquals(0, adjunct.outDegreeOf(v5));
        assertEquals(1, adjunct.inDegreeOf(v5));
        assertEquals(0, adjunct.outDegreeOf(v6));
        DefaultEdge e26 = adjunct.addEdge(v2, v6);
        assertEquals(1, adjunct.inDegreeOf(v6));
        assertTrue(adjunct.edgesOf(v2).contains(e26));
        assertFalse(primary.edgesOf(v2).contains(26));
        assertTrue(adjunct.getEdgeSource(e26).equals(v2));
        assertTrue(adjunct.getEdgeTarget(e26).equals(v6));
        DefaultEdge e13 = adjunct.removeEdge(v1, v3);
        assertFalse(adjunct.containsEdge(e13));
        assertTrue(primary.containsEdge(e13));
        assertFalse(adjunct.removeEdge(e13));		// Can't remove edge twice
        assertFalse(adjunct.outgoingEdgesOf(v1).contains(e13));
        assertFalse(adjunct.incomingEdgesOf(v3).contains(e13));
        DefaultEdge e23d = adjunct.removeEdge(v2, v3);
        assertEquals(e23, e23d);
        assertFalse(adjunct.containsEdge(e23));
        assertFalse(adjunct.outgoingEdgesOf(v2).contains(e23));
        assertFalse(adjunct.incomingEdgesOf(v3).contains(e23));
        DefaultEdge e53 = adjunct.addEdge(v5, v3);
        assertEquals(1, adjunct.outDegreeOf(v5));
        DefaultEdge e37d = adjunct.removeEdge(v3, v7);
        assertEquals(e37, e37d);
        DefaultEdge e47 = adjunct.addEdge(v4, v7);
        DefaultEdge e12 = adjunct.edgesOf(v1).iterator().next();
        assertTrue(adjunct.edgesOf(v1).contains(e12));
        assertTrue(adjunct.edgesOf(v2).contains(e12));
        assertTrue(adjunct.edgesOf(v2).contains(e26));
        assertTrue(adjunct.edgesOf(v2).contains(e25));
        assertEquals(2, adjunct.outDegreeOf(v2));
        assertEquals(1, adjunct.inDegreeOf(v2));
        assertTrue(adjunct.edgesOf(v3).contains(e53));
        DefaultEdge e34 = adjunct.getEdge(v3, v4);
        assertTrue(adjunct.edgesOf(v3).contains(e34));
        assertTrue(adjunct.containsEdge(e47));
        assertTrue(adjunct.outgoingEdgesOf(v1).contains(e12));
        assertTrue(adjunct.incomingEdgesOf(v2).contains(e12));
        assertTrue(adjunct.outgoingEdgesOf(v2).contains(e26));
        assertTrue(adjunct.outgoingEdgesOf(v2).contains(e25));
        assertTrue(adjunct.incomingEdgesOf(v6).contains(e26));
        assertTrue(adjunct.incomingEdgesOf(v5).contains(e25));
        assertTrue(adjunct.outgoingEdgesOf(v5).contains(e53));
        assertTrue(adjunct.incomingEdgesOf(v3).contains(e53));
        assertTrue(adjunct.outgoingEdgesOf(v3).contains(e34));
        assertTrue(adjunct.incomingEdgesOf(v4).contains(e34));
        assertTrue(adjunct.outgoingEdgesOf(v4).contains(e47));
        assertTrue(adjunct.incomingEdgesOf(v7).contains(e47));
        // Add a previously deleted edge
        e13 = adjunct.addEdge(v1, v3);
        assertTrue(adjunct.containsEdge(e13));
        assertTrue(adjunct.edgesOf(v3).contains(e13));
    }

    @Test
    public void testInsertPathGraph() {

        // Construct a graph
        //	V1_____ V2
        //	  \____ v5___v6___V4
        DefaultEdge e13 = adjunct.getEdge(v1, v3);
        DefaultEdge e34 = adjunct.getEdge(v1, v3);
        assertNotNull(e13);
        assertNotNull(e34);
        adjunct.removeVertex(v3);
        assertFalse(adjunct.edgeSet().contains(e13));
        assertFalse(adjunct.edgeSet().contains(e34));
        assertTrue(primary.edgeSet().contains(e13));
        assertTrue(primary.edgeSet().contains(e34));
        adjunct.addVertex(v5);
        adjunct.addVertex(v6);
        DefaultEdge e15 = adjunct.addEdge(v1, v5);
        DefaultEdge e56 = adjunct.addEdge(v5, v6);
        DefaultEdge e64 = adjunct.addEdge(v6, v4);
        assertTrue(adjunct.outgoingEdgesOf(v1).contains(e15));
        assertTrue(adjunct.incomingEdgesOf(v6).contains(e56));
        assertTrue(adjunct.incomingEdgesOf(v4).contains(e64));
        // Transform too
        // 	V1_____ V2
        //	  \____ v5___v7___V4
        adjunct.removeVertex(v6);
        adjunct.addVertex(v7);
        DefaultEdge e74 = adjunct.addEdge(v7, v4);
        DefaultEdge e57 = adjunct.addEdge(v5, v7);
        assertTrue(adjunct.incomingEdgesOf(v4).contains(e74));
        assertTrue(adjunct.incomingEdgesOf(v7).contains(e57));
    }

}
