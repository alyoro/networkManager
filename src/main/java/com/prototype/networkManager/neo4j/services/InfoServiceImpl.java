package com.prototype.networkManager.neo4j.services;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.prototype.networkManager.neo4j.domain.DeviceNode;
import com.prototype.networkManager.neo4j.domain.enums.DeviceType;
import com.prototype.networkManager.neo4j.exceptions.DeviceNotFoundException;
import com.prototype.networkManager.neo4j.repository.DeviceNodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InfoServiceImpl implements InfoService {

    private final DeviceNodeRepository deviceNodeRepository;

    public InfoServiceImpl(DeviceNodeRepository deviceNodeRepository) {
        this.deviceNodeRepository = deviceNodeRepository;
    }

    @Override
    public List<DeviceCount> countingDevices() {
        List<DeviceCount> countDevicesByType = new ArrayList<>();
        for(DeviceType deviceType: DeviceType.values()){
            if(deviceType.equals(DeviceType.None) || deviceType.equals(DeviceType.RoomSocket)) continue;
            countDevicesByType.add(new DeviceCount(
                    deviceType.toString(),
                    deviceNodeRepository.getNumberOfDevicesByType(deviceType.toString())));
        }
        return countDevicesByType;
    }

    @Override
    public Iterable<DeviceNode> devicesLevelUp(Long id) throws DeviceNotFoundException{
        Optional<DeviceNode> deviceNodeOptional = deviceNodeRepository.findById(id);
        if(deviceNodeOptional.isEmpty()){
            throw new DeviceNotFoundException("Device with id: "+id+" not found");
        } else {
            Iterable<DeviceNode> devices = deviceNodeRepository.getDevicesLevelUp(id);
            devices.forEach(device -> device.setDeviceType(DeviceType.valueOf(device.getClass().getSimpleName())));
            return  devices;
        }
    }

    @Override
    public Iterable<DeviceNode> devicesLevelDown(Long id) throws DeviceNotFoundException{
        Optional<DeviceNode> deviceNodeOptional = deviceNodeRepository.findById(id);
        if(deviceNodeOptional.isEmpty()){
            throw new DeviceNotFoundException("Device with id: "+id+" not found");
        } else {
            Iterable<DeviceNode> devices = deviceNodeRepository.getDevicesLevelDown(id);
            devices.forEach(device -> device.setDeviceType(DeviceType.valueOf(device.getClass().getSimpleName())));
            return  devices;
        }
    }

    @JsonSerialize
    public class DeviceCount{
        String type;
        Integer number;

        public DeviceCount(String type, Integer number) {
            this.type = type;
            this.number = number;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }
    }
}
