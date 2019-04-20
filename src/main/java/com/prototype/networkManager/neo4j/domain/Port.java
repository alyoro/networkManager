package com.prototype.networkManager.neo4j.domain;

import com.prototype.networkManager.neo4j.domain.enums.DeviceType;
import com.prototype.networkManager.neo4j.domain.enums.PortSpeed;
import com.prototype.networkManager.neo4j.domain.enums.PortStatus;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

@QueryResult
public class Port extends Node {

    private int portNumber;
    private DeviceType devicePlugged;
    private String portOnTheUpperElement;
    private PortSpeed portSpeed;
    private PortStatus portStatus;


    @Relationship(type = "CONNECTION", direction = Relationship.UNDIRECTED)
    private List<Connection> connections;

    @Override
    public String toString() {
        return "Port{" +
                "portNumber=" + portNumber +
                ", devicePlugged=" + devicePlugged +
                ", portOnTheUpperElement='" + portOnTheUpperElement + '\'' +
                ", portSpeed=" + portSpeed +
                ", portStatus=" + portStatus +
                ", connections=" + connections +
                '}';
    }

    public Port() {
    }

    public Port(int portNumber, DeviceType devicePlugged, String portOnTheUpperElement, PortSpeed portSpeed, PortStatus portStatus, List<Connection> connections) {
        this.portNumber = portNumber;
        this.devicePlugged = devicePlugged;
        this.portOnTheUpperElement = portOnTheUpperElement;
        this.portSpeed = portSpeed;
        this.portStatus = portStatus;
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

    public PortSpeed getPortSpeed() {
        return portSpeed;
    }

    public void setPortSpeed(PortSpeed portSpeed) {
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

