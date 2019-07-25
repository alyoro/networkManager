package com.prototype.networkManager.neo4j.services;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.prototype.networkManager.neo4j.domain.DeviceNode;
import com.prototype.networkManager.neo4j.domain.enums.DeviceType;
import com.prototype.networkManager.neo4j.exceptions.DeviceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class InfoServiceImpl implements InfoService {

    private final DeviceNodeService deviceNodeService;

    @Override
    public List<DeviceCount> countingDevices() {
        List<DeviceCount> countDevicesByType = new ArrayList<>();
        for (DeviceType deviceType : DeviceType.values()) {
            if (deviceType.equals(DeviceType.None) || deviceType.equals(DeviceType.RoomSocket)) continue;
            countDevicesByType.add(new DeviceCount(
                    deviceType.toString(),
                    deviceNodeService.getNumberOfDevicesByType(deviceType.toString())));
        }
        return countDevicesByType;
    }

    @Override
    public DeviceNode connectedDeviceByPortId(Long portId) throws DeviceNotFoundException {
        DeviceNode deviceNode = deviceNodeService.getDeviceNodeByIdOfPort(portId);
        deviceNode = deviceNodeService.getDeviceNodeById(deviceNode.getId(), 3);
        deviceNode.setDeviceType(DeviceType.valueOf(deviceNode.getClass().getSimpleName()));
        return deviceNode;
    }

    @Data
    @AllArgsConstructor
    @JsonSerialize
    public class DeviceCount {
        String type;
        Integer number;
    }
}
