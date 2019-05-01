package com.prototype.networkManager.neo4j.domain;

public class Server extends DeviceNode {

    private String localization;
    private String ip;

    public Server() {
    }

    public Server(String localization, String ip) {
        this.localization = localization;
        this.ip = ip;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
