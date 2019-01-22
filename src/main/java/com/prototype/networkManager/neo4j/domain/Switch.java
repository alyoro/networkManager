package com.prototype.networkManager.neo4j.domain;

public class Switch extends Node {

    private String identifier;
    private String localization;
    private String dateOfPurchase;
    private int numberOfPorts;
    private String managementIP;

    public Switch() {
    }

    public Switch(String identifier, String localization, String dateOfPurchase, int numberOfPorts, String managementIP) {
        this.identifier = identifier;
        this.localization = localization;
        this.dateOfPurchase = dateOfPurchase;
        this.numberOfPorts = numberOfPorts;
        this.managementIP = managementIP;
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

    public String getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public int getNumberOfPorts() {
        return numberOfPorts;
    }

    public void setNumberOfPorts(int numberOfPorts) {
        this.numberOfPorts = numberOfPorts;
    }

    public String getManagementIP() {
        return managementIP;
    }

    public void setManagementIP(String managementIP) {
        this.managementIP = managementIP;
    }
}
