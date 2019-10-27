package com.prototype.networkManager.neo4j.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchPanel extends DeviceNode {

    private String building;
    private String room;
    private String localization;
    private String description;
}
