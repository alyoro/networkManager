package com.prototype.networkManager.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

@Data
@NoArgsConstructor
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
}
