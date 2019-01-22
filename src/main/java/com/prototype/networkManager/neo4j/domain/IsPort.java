package com.prototype.networkManager.neo4j.domain;

import org.neo4j.ogm.annotation.RelationshipEntity;

@RelationshipEntity(type = "IS_PORT")
public class IsPort {
    private String name;

    public IsPort() {
    }

    public IsPort(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
