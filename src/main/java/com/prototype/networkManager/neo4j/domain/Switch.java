package com.prototype.networkManager.neo4j.domain;

public class Switch extends DeviceNode {

    private String identifier;
    private String localization;
    private String dateOfPurchase;
    private String managementIP;

    public Switch() {
    }

    public Switch(String identifier, String localization, String dateOfPurchase, String managementIP) {
        this.identifier = identifier;
        this.localization = localization;
        this.dateOfPurchase = dateOfPurchase;
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

    public String getManagementIP() {
        return managementIP;
    }

    public void setManagementIP(String managementIP) {
        this.managementIP = managementIP;
    }
}
