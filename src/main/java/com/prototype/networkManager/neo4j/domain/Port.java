package com.prototype.networkManager.neo4j.domain;

import com.prototype.networkManager.neo4j.domain.enums.PortStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@QueryResult
public class Port extends Node {

    private int portNumber;
    private String devicePlugged;
    private String portOnTheOtherElement;
    private String portSpeed;
    private PortStatus portStatus;
    private boolean logical;
    private List<String> vlans;

    @Relationship(type = "CONNECTION", direction = Relationship.UNDIRECTED)
    private List<Connection> connections;

    public Port(int portNumber, String devicePlugged, String portOnTheOtherElement, String portSpeed, PortStatus portStatus, boolean logical, List<String> vlans) {
        this.portNumber = portNumber;
        this.devicePlugged = devicePlugged;
        this.portOnTheOtherElement = portOnTheOtherElement;
        this.portSpeed = portSpeed;
        this.portStatus = portStatus;
        this.logical = logical;
        this.vlans = vlans;
    }
}

