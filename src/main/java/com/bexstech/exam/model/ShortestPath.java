package com.bexstech.exam.model;

import java.util.*;

public class ShortestPath extends AbstractCollection<Edge> {
    private List<Edge> pathList;
    private Vertex source;
    private Vertex destination;
    private Integer price;

    public ShortestPath(Vertex startingVertex) {
        pathList = new LinkedList<>();
        source = startingVertex;
        destination = startingVertex;
        price = 0;
    }

    public ShortestPath(ShortestPath other) {
        this.pathList = new LinkedList<>(other.pathList);
        this.source = other.source;
        this.destination = other.destination;
        this.price = other.price;
    }

    public void addEdge(Edge edge) {
        pathList.add(edge);
    }

    @Override
    public Iterator<Edge> iterator() {
        return pathList.iterator();
    }

    @Override
    public int size() {
        return pathList.size();
    }

    @Override
    public String toString() {
        return "best route: " + pathListToString(" - ") + " > $" + price;
    }

    public String pathListToString(String delimiter) {
        StringJoiner stringJoiner = new StringJoiner(delimiter);
        Vertex current = source;
        stringJoiner.add(source.getLabel());
        for (Edge e : pathList) {
            current = e.getNeighbor(current);
            stringJoiner.add(current.getLabel());
        }

        return stringJoiner.toString();
    }

    public void setPrice(Integer distance) {
        this.price = distance;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }
}
