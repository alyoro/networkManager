package com.prototype.networkManager.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

public class Port extends Node {

    private int portNumber;
    private DeviceType devicePlugged;
    private String portOnTheUpperElement;

    @Relationship(type = "CONNECTION", direction = Relationship.UNDIRECTED)
    @JsonManagedReference
    private List<Connection> connections;

    public Port() {
    }

    public Port(int portNumber, DeviceType devicePlugged, String portOnTheUpperElement, List<Connection> connections) {
        this.portNumber = portNumber;
        this.devicePlugged = devicePlugged;
        this.portOnTheUpperElement = portOnTheUpperElement;
        this.connections = connections;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public DeviceType getDevicePlugged() {
        return devicePlugged;
    }

    public void setDevicePlugged(DeviceType devicePlugged) {
        this.devicePlugged = devicePlugged;
    }

    public String getPortOnTheUpperElement() {
        return portOnTheUpperElement;
    }

    public void setPortOnTheUpperElement(String portOnTheUpperElement) {
        this.portOnTheUpperElement = portOnTheUpperElement;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }
}
