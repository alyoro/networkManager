package com.prototype.networkManager.neo4j.domain;

public class DeviceNode extends Node{

    public  DeviceNode(){}

    public DeviceNode(int numberOfPorts) {
        this.numberOfPorts = numberOfPorts;
    }

    public DeviceNode(Long id, int numberOfPorts) {
        super(id);
        this.numberOfPorts = numberOfPorts;
    }

    private int numberOfPorts;

    public int getNumberOfPorts() {
        return numberOfPorts;
    }

    public void setNumberOfPorts(int numberOfPorts) {
        this.numberOfPorts = numberOfPorts;
    }
}
