package com.bexstech.exam.engine;

import com.bexstech.exam.exception.BadRequestException;
import com.bexstech.exam.model.Edge;
import com.bexstech.exam.model.Graph;
import com.bexstech.exam.model.ShortestPath;
import com.bexstech.exam.model.Vertex;

import java.util.HashSet;
import java.util.Set;

public class DijkstraAlgorithm {
    private Set<Vertex> unsettledNodes;
    private Vertex currentVertex;
    private Graph graph;

    public DijkstraAlgorithm(Graph graph) {
        this.graph = graph;
    }

    public ShortestPath calculateShortestPath(Vertex source, Vertex destination) throws BadRequestException {
        if (graph.getChosenShortestPath() != null) {
            graph.resetGraph();
        }
        source.setAsSource();
        unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0 && !destination.equals(currentVertex)) {
            currentVertex = getCheapestDistanceVertex();
            for (Edge e : currentVertex) {
                updateNeighborDistance(e);
            }
        }

        ShortestPath sp = destination.getCheapestPath();
        if (sp == null) {
            throw new BadRequestException("Route from '" + source + "' to '" + destination + "' doesn't exist");
        }
        this.graph.setChosenShortestPath(sp);
        return sp;
    }

    public ShortestPath calculateShortestPath(String sourceLabel, String destinationLabel) throws BadRequestException {
        return calculateShortestPath(graph.getVertex(sourceLabel), graph.getVertex(destinationLabel));
    }

    private Vertex getCheapestDistanceVertex() {
        Vertex lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Vertex v : unsettledNodes) {
            int nodeDistance = v.getPrice();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = v;
            }
        }
        lowestDistanceNode.visit();
        unsettledNodes.remove(currentVertex);
        return lowestDistanceNode;
    }

    private void updateNeighborDistance(Edge e) {
        Vertex neighbor = e.getNeighbor(currentVertex);
        if (!neighbor.getVisited()) {
            Integer newDistance = currentVertex.getPrice() + e.getPrice();
            if (neighbor.getPrice() > newDistance) {
                neighbor.setPrice(newDistance);
                ShortestPath shortestPath = new ShortestPath(currentVertex.getCheapestPath());
                shortestPath.addEdge(e);
                neighbor.setCheapestPath(shortestPath);
                unsettledNodes.add(neighbor);
            }
        }
    }
}
