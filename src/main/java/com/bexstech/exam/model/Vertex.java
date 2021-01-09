package com.bexstech.exam.model;

import java.util.ArrayList;
import java.util.Iterator;

public class Vertex implements Iterable<Edge> {

    private ArrayList<Edge> neighborhood;
    private String label;
    private Integer price;
    private ShortestPath shortestPath;
    private Boolean visited;

    public Vertex(String label) {
        this.label = label;
        this.price = Integer.MAX_VALUE;
        this.neighborhood = new ArrayList<>();
        this.visited = false;
    }

    public String getLabel(){
        return this.label;
    }

    @Override
    public Iterator<Edge> iterator() {
        return neighborhood.iterator();
    }

    @Override
    public String toString() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return label.equals(vertex.label);
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public boolean containsNeighbor(Edge edge) {
        return this.neighborhood.contains(edge);
    }

    public void addNeighbor(Edge edge){
        if (!neighborhood.contains(edge)){
            this.neighborhood.add(edge);
        }
    }

    public void setAsSource(){
        visited = true;
        price = 0;
        shortestPath = new ShortestPath(this);
    }

    public ShortestPath getCheapestPath() {
        return shortestPath;
    }

    public void setCheapestPath(ShortestPath shortestPath) {
        shortestPath.setPrice(this.price);
        shortestPath.setDestination(this);
        this.shortestPath = shortestPath;
    }

    public void reset(){
        this.price = Integer.MAX_VALUE;
        this.visited = false;
        this.shortestPath = null;
    }

    public Boolean getVisited() {
        return visited;
    }

    public void visit(){
        visited = true;
    }

}
