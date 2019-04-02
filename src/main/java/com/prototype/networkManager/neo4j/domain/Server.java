package com.prototype.networkManager.neo4j.domain;

import java.util.List;

public class Server extends DeviceNode {

    private String localization;
    private String ip;
//    private List<String> vlans;

    public Server() {
    }

    public Server(String localization, String ip/*, List<String> vlans*/) {
        this.localization = localization;
        this.ip = ip;
//        this.vlans = vlans;
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

//    public List<String> getVlans() {
//        return vlans;
//    }
//
//    public void setVlans(List<String> vlans) {
//        this.vlans = vlans;
//    }


}
