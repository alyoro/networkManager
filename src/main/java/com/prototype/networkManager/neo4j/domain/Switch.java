package com.prototype.networkManager.neo4j.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Switch extends DeviceNode {

    private String localization;
    private String dateOfPurchase;
    private String managementIP;
}
