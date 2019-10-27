package com.prototype.networkManager.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.prototype.networkManager.neo4j.domain.enums.DeviceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DeviceNode extends Node {

    private int numberOfPorts;

    @Index(unique=true)
    private String identifier;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private DeviceType deviceType;

    @Relationship(type = "IS_PORT", direction = Relationship.INCOMING)
    private List<Port> ports;

    @JsonIgnore
    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }
}
