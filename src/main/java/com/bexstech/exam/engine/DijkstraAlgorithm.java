package com.bexstech.exam.engine;

import com.bexstech.exam.exception.BadRequestException;
import com.bexstech.exam.model.CheapestPath;
import com.bexstech.exam.model.Edge;
import com.bexstech.exam.model.Graph;
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

    public CheapestPath calculateShortestPath(Vertex source, Vertex destination) throws BadRequestException {
        if (graph.getChosenShortestPath() != null) {
            graph.resetGraph();
        }
        source.setAsSource();
        unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0 && !destination.equals(currentVertex)) {
            currentVertex = getCheapestVertex();
            for (Edge e : currentVertex) {
                updateCheapestNeighbor(e);
            }
        }

        CheapestPath sp = destination.getCheapestPath();
        if (sp == null) {
            graph.resetGraph();
            throw new BadRequestException("Route from '" + source + "' to '" + destination + "' doesn't exist");
        }
        this.graph.setChosenShortestPath(sp);
        return sp;
    }

    public CheapestPath calculateShortestPath(String sourceLabel, String destinationLabel) throws BadRequestException {
        return calculateShortestPath(graph.getVertex(sourceLabel), graph.getVertex(destinationLabel));
    }

    private Vertex getCheapestVertex() {
        Vertex cheapestNode = null;
        int cheapestPrice = Integer.MAX_VALUE;
        for (Vertex v : unsettledNodes) {
            int nodePrice = v.getPrice();
            if (nodePrice < cheapestPrice) {
                cheapestPrice = nodePrice;
                cheapestNode = v;
            }
        }
        cheapestNode.visit();
        unsettledNodes.remove(currentVertex);
        return cheapestNode;
    }

    private void updateCheapestNeighbor(Edge e) {
        Vertex neighbor = e.getNeighbor();
        if (!neighbor.getVisited()) {
            Integer newPrice = currentVertex.getPrice() + e.getPrice();
            if (neighbor.getPrice() > newPrice) {
                neighbor.setPrice(newPrice);
                CheapestPath cheapestPath = new CheapestPath(currentVertex.getCheapestPath());
                cheapestPath.addEdge(e);
                neighbor.setCheapestPath(cheapestPath);
                unsettledNodes.add(neighbor);
            }
        }
    }
}
