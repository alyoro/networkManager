package com.prototype.networkManager.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.prototype.networkManager.neo4j.domain.enums.DeviceType;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

public class DeviceNode extends Node {

    private int numberOfPorts;
    private String identifier;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private DeviceType deviceType;

    @Relationship(type = "IS_PORT", direction = Relationship.INCOMING)
    private List<Port> ports;

    public DeviceNode() {
    }

    public int getNumberOfPorts() {
        return numberOfPorts;
    }

    public void setNumberOfPorts(int numberOfPorts) {
        this.numberOfPorts = numberOfPorts;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<Port> getPorts() {
        return ports;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    @JsonIgnore
    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }
}
