package com.prototype.networkManager.neo4j.domain;

import org.neo4j.ogm.annotation.Labels;

import java.util.ArrayList;
import java.util.List;

public class PatchPanel extends DeviceNode {

    private String building;
    private String room;
    private String identifier;
    private String localization;
    private String description;

    @Labels
    private List<String> labels = new ArrayList<>();


    public PatchPanel() {
    }

    public PatchPanel(String building, String room, String identifier, String localization, String description) {
        this.building = building;
        this.room = room;
        this.identifier = identifier;
        this.localization = localization;
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

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}
