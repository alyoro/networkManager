package com.prototype.networkManager.neo4j.domain;

import org.neo4j.ogm.annotation.RelationshipEntity;

import java.util.List;

@RelationshipEntity(type = "CONNECTION")
public class Connection extends Edge {

    private List<String> vlans;

    public Connection() {
    }

    public Connection(Port pM, Port pS){
        super();
        this.setStartNode(pS);
        this.setEndNode(pM);
    }

    public Connection(List<String> vlans) {
        this.vlans = vlans;
    }

    public List<String> getVlans() {
        return vlans;
    }

    public void setVlans(List<String> vlans) {
        this.vlans = vlans;
    }

}
