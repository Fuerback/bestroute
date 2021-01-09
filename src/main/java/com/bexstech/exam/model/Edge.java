package com.bexstech.exam.model;

public class Edge {

    private Vertex source;
    private Vertex destination;
    private Integer price;

    public Edge(Vertex source, Vertex destination, int price) {
        if (price < 1) {
            throw new IllegalArgumentException("The price has to be an positive integer!");
        }
        this.source = source;
        this.destination = destination;
        this.price = price;
    }

    public Vertex getNeighbor() {
        return destination;
    }

    public int getPrice() {
        return this.price;
    }

    public boolean equals(Object other) {
        if (!(other instanceof Edge)) {
            return false;
        }

        Edge e = (Edge) other;
        return e.source.equals(this.source) && e.destination.equals(this.destination);
    }

    public int hashCode() {
        return (source.getLabel() + destination.getLabel()).hashCode();
    }
}
