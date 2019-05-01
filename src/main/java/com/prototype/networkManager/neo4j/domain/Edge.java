package com.prototype.networkManager.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity
public class Edge {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    @JsonIgnore
    private Node startNode;

    @EndNode
    @JsonIgnore
    private Node endNode;


    public Edge() {
    }

    public Edge(Long id, Node startNode, Node endNode) {
        this.id = id;
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }
}
