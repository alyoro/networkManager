package com.prototype.networkManager.neo4j.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class RoomSocket extends DeviceNode {

    private String building;
    private String room;
    private String description;

}
