package com.prototype.networkManager.neo4j.domain;

import com.prototype.networkManager.neo4j.domain.enums.DeviceType;
import com.prototype.networkManager.neo4j.domain.PortSpeed;
import com.prototype.networkManager.neo4j.domain.enums.PortStatus;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

@QueryResult
public class Port extends Node {

    private int portNumber;
    private String devicePlugged;
    private String portOnTheOtherElement;
    private String portSpeed;
    private PortStatus portStatus;


    @Relationship(type = "CONNECTION", direction = Relationship.UNDIRECTED)
    private List<Connection> connections;

    public Port() {
    }

    public Port(int portNumber, String devicePlugged, String portOnTheOtherElement, String portSpeed, PortStatus portStatus) {
        this.portNumber = portNumber;
        this.devicePlugged = devicePlugged;
        this.portOnTheOtherElement = portOnTheOtherElement;
        this.portSpeed = portSpeed;
        this.portStatus = portStatus;
    }

    @Override
    public String toString() {
        return "Port{" +
                "portNumber=" + portNumber +
                ", devicePlugged=" + devicePlugged +
                ", portOnTheOtherElement='" + portOnTheOtherElement + '\'' +
                ", portSpeed=" + portSpeed +
                ", portStatus=" + portStatus +
                ", connections=" + connections +
                '}';
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public String getDevicePlugged() {
        return devicePlugged;
    }

    public void setDevicePlugged(String devicePlugged) {
        this.devicePlugged = devicePlugged;
    }

    public String getPortOnTheOtherElement() {
        return portOnTheOtherElement;
    }

    public void setPortOnTheOtherElement(String portOnTheOtherElement) {
        this.portOnTheOtherElement = portOnTheOtherElement;
    }

    public String getPortSpeed() {
        return portSpeed;
    }

    public void setPortSpeed(String portSpeed) {
        this.portSpeed = portSpeed;
    }

    public PortStatus getPortStatus() {
        return portStatus;
    }

    public void setPortStatus(PortStatus portStatus) {
        this.portStatus = portStatus;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }
}

