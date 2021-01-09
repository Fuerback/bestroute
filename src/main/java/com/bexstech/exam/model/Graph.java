package com.bexstech.exam.model;

import com.bexstech.exam.exception.BadRequestException;

import java.util.HashMap;

public class Graph {

    private HashMap<String, Vertex> vertices;
    private HashMap<Integer, Edge> edges;
    private CheapestPath chosenCheapestPath;

    public Graph() {
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
    }

    public boolean addConnected(String vertex1, String vertex2, int price) {
        Vertex source = vertices.computeIfAbsent(vertex1, Vertex::new);
        Vertex destination = vertices.computeIfAbsent(vertex2, Vertex::new);
        return addEdge(source, destination, price);
    }

    public boolean addEdge(Vertex source, Vertex destination, int price) {
        if (source.equals(destination)) {
            return false;
        }

        Edge e = new Edge(source, destination, price);
        if (edges.containsKey(e.hashCode())) {
            return false;
        } else if (!(vertices.containsValue(source) || vertices.containsValue(destination))) {
            return false;
        } else if (source.containsNeighbor(e) || destination.containsNeighbor(e)) {
            return false;
        }

        edges.put(e.hashCode(), e);
        source.addNeighbor(e);
        destination.addNeighbor(e);

        return true;
    }

    public Vertex getVertex(String label) throws BadRequestException {
        if (vertices.containsKey(label)) {
            return vertices.get(label);
        }
        throw new BadRequestException("'" + label + "' vertex not found in graph");
    }

    public void resetGraph() {
        vertices.values().forEach(Vertex::reset);
        chosenCheapestPath = null;
    }

    public CheapestPath getChosenShortestPath() {
        return chosenCheapestPath;
    }

    public void setChosenShortestPath(CheapestPath chosenCheapestPath) {
        this.chosenCheapestPath = chosenCheapestPath;
    }
}
