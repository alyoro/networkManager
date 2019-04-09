package com.prototype.networkManager.neo4j.domain;

public class RoomSocket extends DeviceNode {

    private String building;
    private String room;
    private String identifier;
    private String description;

    public RoomSocket() {
    }

    public RoomSocket(String building, String room, String identifier, String description) {
        this.building = building;
        this.room = room;
        this.identifier = identifier;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
