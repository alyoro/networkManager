package com.prototype.networkManager.neo4j.domain;

import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

public class DeviceNode extends Node{

    private int numberOfPorts;

    @Relationship(type = "IS_PORT", direction = Relationship.INCOMING)
    private List<Port> ports;

    public  DeviceNode(){}

    public DeviceNode(int numberOfPorts, List<Port> ports) {
        this.numberOfPorts = numberOfPorts;
        this.ports = ports;
    }

    public int getNumberOfPorts() {
        return numberOfPorts;
    }

    public void setNumberOfPorts(int numberOfPorts) {
        this.numberOfPorts = numberOfPorts;
    }

    public List<Port> getPorts() {
        return ports;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }
}
