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

    private DirectedGraph<String, DefaultEdge> primary;
    private SimpleDirectedAdjunctGraph<String, DefaultEdge> adjunct;


    public SimpleDirectedAdjunctGraphTest(String name) {
        super(name);
    }

    @Override
    protected void setUp() throws Exception
    {
        primary = new SimpleDirectedGraph<String, DefaultEdge>(
                DefaultEdge.class);
        primary.addVertex(v1);
        primary.addVertex(v2);
        primary.addEdge(v1, v2);
        primary.addEdge(v2, v1);
        adjunct = new SimpleDirectedAdjunctGraph<String, DefaultEdge>(primary);

    }

    @Test
    public void testAccessBase() {

        assertTrue(adjunct.containsVertex(v1));
        assertTrue(adjunct.containsVertex(v2));
        DefaultEdge e1 = adjunct.getEdge(v1, v2);
        assertNotNull(e1);
        DefaultEdge e2 = adjunct.getEdge(v2, v1);
        assertNotNull(e2);
        int deg = adjunct.inDegreeOf(v1);
        assertEquals(1, deg);
        deg = adjunct.inDegreeOf(v2);
        assertEquals(1, deg);
        deg = adjunct.outDegreeOf(v1);
        assertEquals(1, deg);
        deg = adjunct.outDegreeOf(v2);
        assertEquals(1, deg);
        Set<DefaultEdge> edges = adjunct.incomingEdgesOf(v1);
        assertTrue(edges.contains(e2));
        edges = adjunct.incomingEdgesOf(v2);
        assertTrue(edges.contains(e1));
        edges = adjunct.outgoingEdgesOf(v1);
        assertTrue(edges.contains(e1));
        edges = adjunct.outgoingEdgesOf(v2);
        assertTrue(edges.contains(e2));
    }

    @Test
    public void testProtectedBase() {

         adjunct.removeVertex(v1);
         assertFalse(adjunct.containsVertex(v1));
         assertTrue(primary.containsVertex(v1));
         DefaultEdge e1 = adjunct.getEdge(v1, v2);
         assertNotNull(e1);
         adjunct.removeEdge(e1);
         assertFalse(adjunct.containsEdge(e1));
         adjunct.removeEdge(v1, v2);
         assertFalse(adjunct.containsEdge(e1));
         assertTrue(primary.containsEdge(e1));
    }

    @Test
    public void testJointGraph() {

        adjunct.addVertex(v3);
        assertTrue(adjunct.containsVertex(v3));
        adjunct.addVertex(v4);
        DefaultEdge e1 = adjunct.addEdge(v3, v4);
        assertTrue(adjunct.containsEdge(e1));
        DefaultEdge e2 = adjunct.addEdge(v1, v3);
        DefaultEdge e3 = adjunct.addEdge(v1, v4);
        assertTrue(adjunct.containsEdge(e2));
        assertTrue(adjunct.containsEdge(e3));
        assertTrue(adjunct.edgeSet().contains(e1));
        assertTrue(adjunct.edgeSet().contains(e2));
        assertTrue(adjunct.edgeSet().contains(e3));
        assertTrue(adjunct.vertexSet().contains(v2));
        assertTrue(adjunct.vertexSet().contains(v4));
        int deg = adjunct.inDegreeOf(v3);
        assertEquals(1, deg);
        deg = adjunct.inDegreeOf(v4);
        assertEquals(2, deg);
        deg = adjunct.outDegreeOf(v1);
        assertEquals(3, deg);
        deg = adjunct.outDegreeOf(v3);
        assertEquals(1, deg);
        Set<DefaultEdge> edges = adjunct.incomingEdgesOf(v4);
        assertTrue(edges.contains(e1));
        assertTrue(edges.contains(e3));
        edges = adjunct.incomingEdgesOf(v3);
        assertTrue(edges.contains(e2));
        edges = adjunct.outgoingEdgesOf(v1);
        assertTrue(edges.contains(e2));
        assertTrue(edges.contains(e3));
        edges = adjunct.outgoingEdgesOf(v3);
        assertTrue(edges.contains(e1));
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
        DefaultEdge e3 = adjunct.addEdge(v1, v4);

        int deg = adjunct.inDegreeOf(v1);
        assertEquals(1, deg);
        deg = adjunct.adjunctInDegreeOf(v1);
        assertEquals(0, deg);
        deg = adjunct.outDegreeOf(v1);
        assertEquals(2, deg);
        deg = adjunct.adjunctOutDegreeOf(v1);
        assertEquals(1, deg);
        Set<DefaultEdge> edges = adjunct.incomingEdgesOf(v2);
        assertTrue(edges.contains(e1));
        edges = adjunct.adjunctIncomingEdgesOf(v2);
        assertFalse(edges.contains(e1));
        edges = adjunct.outgoingEdgesOf(v1);
        assertTrue(edges.contains(e1));
        assertTrue(edges.contains(e3));
        edges = adjunct.adjunctOutgoingEdgesOf(v1);
        assertFalse(edges.contains(e1));
        assertTrue(edges.contains(e3));

    }

}
