package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.DeviceNode;
import com.prototype.networkManager.neo4j.exceptions.DeviceNotFoundException;

import java.util.List;

public interface InfoService {

    List<InfoServiceImpl.DeviceCount> countingDevices();
    DeviceNode connectedDeviceByPortId(Long portId) throws DeviceNotFoundException;
}
