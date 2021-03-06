package com.bexstech.exam.model;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

import com.bexstech.exam.dto.RouteResponseDTO;

public class CheapestPath extends AbstractCollection<Edge> {
    private List<Edge> pathList;
    private Vertex source;
    private Vertex destination;
    private Integer price;

    public CheapestPath(Vertex startingVertex) {
        pathList = new LinkedList<>();
        source = startingVertex;
        destination = startingVertex;
        price = 0;
    }

    public CheapestPath(CheapestPath other) {
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
        return pathListToString(" - ") + " > $" + price;
    }

    public RouteResponseDTO toRouteResponseDTO() {
        return new RouteResponseDTO( pathListToString(" - "), price );
    }

    public String pathListToString(String delimiter) {
        StringJoiner stringJoiner = new StringJoiner(delimiter);
        Vertex current;
        stringJoiner.add(source.getLabel());
        for (Edge e : pathList) {
            current = e.getNeighbor();
            stringJoiner.add(current.getLabel());
        }

        return stringJoiner.toString();
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }
}
