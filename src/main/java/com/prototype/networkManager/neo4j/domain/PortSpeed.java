package com.prototype.networkManager.neo4j.domain;

import java.util.List;

public class PortSpeed extends Node{

    private List<String> names;

    public PortSpeed() {
    }

    public PortSpeed(List<String> names) {
        this.names = names;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}
