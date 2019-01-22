package com.prototype.networkManager.neo4j.domain;

import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

public class PatchPanel extends Node {

    private String building;
    private String room;
    private String identifier;
    private String localization;
    private String description;
    private int numberOfPorts;

    @Relationship(type = "IS_PORT", direction = Relationship.INCOMING)
    private List<Port> ports;

    public PatchPanel() {
    }

    public PatchPanel(String building, String room, String identifier, String localization, String description, int numberOfPorts) {
        this.building = building;
        this.room = room;
        this.identifier = identifier;
        this.localization = localization;
        this.description = description;
        this.numberOfPorts = numberOfPorts;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
