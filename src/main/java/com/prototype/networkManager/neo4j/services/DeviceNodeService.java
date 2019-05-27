package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.DeviceNode;
import com.prototype.networkManager.neo4j.domain.enums.DeviceType;
import com.prototype.networkManager.neo4j.exceptions.DeviceNotFoundException;

public interface DeviceNodeService {

    DeviceNode getDeviceByPortId(Long portId) throws DeviceNotFoundException;

    DeviceType getDeviceNodeTypeByIdOfPort(Long portId) throws DeviceNotFoundException;
}
