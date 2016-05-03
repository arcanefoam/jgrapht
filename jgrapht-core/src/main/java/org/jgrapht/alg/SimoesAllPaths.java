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
package org.jgrapht.alg;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graphs;
import org.jgrapht.SimoesGraphPath;
import org.jgrapht.SimoesGraphPathImpl;
import org.jgrapht.alg.util.Pair;

/**
 * The Class SimoesAllPaths. Finds all the paths in a directed graph, following the directed edges
 * in forward direction. Adapted from: "Simões, R., APAC: An exact algorithm for retrieving cycles
 * and paths in all kinds of graphs, Tékhne-Revista de Estudos Politécnicos, Instituto Politécnico do Cávado e do
 * Ave, 2009, 39-55",  to use edges instead of vertices.
 *
 * @param <V> the value type
 * @param <E> the element type
 */
public class SimoesAllPaths<V, E> {


    /** The paths. */
    private List<SimoesGraphPath<V, E>> paths = new ArrayList<SimoesGraphPath<V, E>>();

    /** The loop paths. */
    private List<SimoesGraphPath<V, E>> loopPaths = new ArrayList<SimoesGraphPath<V, E>>();

    /** A convenient map for easy access */
    private Map<V, List<SimoesGraphPath<V, E>>> pathsFrom = new HashMap<V, List<SimoesGraphPath<V, E>>>();

    /** The graph. */
    private final DirectedGraph<V, E> graph;


    /**
     * Instantiates a new simoes all paths.
     *
     * @param graph the graph
     */
    public SimoesAllPaths(DirectedGraph<V, E> graph) {
        super();
        this.graph = graph;
    }

    public void findAllPaths() {
        findAllPathsBetweenVertices(graph.vertexSet());
    }

    public void findAllPathsBetweenVertices(Set<V>  vxs) {
//        Set<MappingAction> vxs = new HashSet<MappingAction>(this.vertexSet().stream()
//                .filter(MappingAction.class::isInstance)
//                .map(MappingAction.class::cast)
//                .collect(Collectors.toSet()));
        List<Pair<V, V>> pairs = Pair.pairCombinations(vxs);
        for (Pair<V, V> pair : pairs) {
            findAllPaths(pair.first, pair.second);
        }
    }

    public List<SimoesGraphPath<V, E>> getPathsFrom(V source) {
        return pathsFrom.get(source);
    }


    /**
     * Find all paths.
     *
     * @param source the source
     * @param target the target
     */
    private void findAllPaths(V source, V target) {
        Deque<SimoesGraphPath<V, E>> pathStack = new LinkedList<>();
        for (E e : graph.outgoingEdgesOf(source)) {
            SimoesGraphPath<V, E> path = new SimoesGraphPathImpl<V, E>(graph, e);
            pathStack.push(path);
        }
        SimoesGraphPath<V, E> path;
        while (!pathStack.isEmpty()) {
            path = pathStack.pop();
            V tailVertex = path.getEndVertex();
            if (tailVertex.equals(target)) {
                addPath(path);
                continue;
            }
            if (tailVertex.equals(source)) {
                addCycle(path, tailVertex);
            }
            for (E outEdge : graph.outgoingEdgesOf(tailVertex)) {
                SimoesGraphPath<V, E> newPath = new SimoesGraphPathImpl<V, E>(graph, path.getEdgeList());
                newPath.addEdge(outEdge);
                V edgeTarget = graph.getEdgeTarget(outEdge);
                if (!path.contains(edgeTarget)) {
                    if (target.equals(edgeTarget)) {
                        addPath(newPath);
                    } else {
                        pathStack.push(newPath);
                    }
                } else { //if (outEdge.getTarget() instanceof MappingAction) {
                    addCycle(newPath, edgeTarget);
                }
            }
        }
    }


    /**
     * Adds the path.
     *
     * @param newPath the new path
     */
    private void addPath(SimoesGraphPath<V, E> newPath) {
        paths.add(newPath);
        V source = newPath.getStartVertex();
        List<SimoesGraphPath<V, E>> paths = pathsFrom.get(source);
        if (paths == null) {
            paths = new ArrayList<SimoesGraphPath<V,E>>();
            pathsFrom.put(source, paths);
        }
        // No duplicate paths
        if (!paths.stream().anyMatch(ep -> Graphs.getPathVertexList(ep).equals(Graphs.getPathVertexList(newPath)))) {
            paths.add(newPath);
        }
    }


    /**
     * Adds the cycle.
     *
     * @param path the path
     * @param loopVertex the tail vertex
     */
    private void addCycle(SimoesGraphPath<V, E> path, V loopVertex) {
        // Reduce the path to the loop
        List<E> edgeList = path.getEdgeList();
        E startEdge = edgeList.stream()
                .filter(e -> graph.getEdgeSource(e).equals(loopVertex))
                .findFirst()
                .get();
        List<E> loopEdges = edgeList.subList(edgeList.indexOf(startEdge), edgeList.size());
        SimoesGraphPath<V, E> loopPath = new SimoesGraphPathImpl<V, E> (graph, loopEdges);
        loopPath.setLoopVertex(loopVertex);
        loopPaths.add(loopPath);
        V source = loopPath.getStartVertex();
        List<SimoesGraphPath<V, E>> paths = pathsFrom.get(source);
        if (paths == null) {
            paths = new ArrayList<SimoesGraphPath<V,E>>();
            pathsFrom.put(source, paths);
        }
        // No duplicate paths
        if (!paths.stream().anyMatch(ep -> Graphs.getPathVertexList(ep).equals(Graphs.getPathVertexList(loopPath)))) {
            paths.add(loopPath);
        }
    }

}
