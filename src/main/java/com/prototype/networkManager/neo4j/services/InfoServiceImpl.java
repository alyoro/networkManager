package com.prototype.networkManager.neo4j.services;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.prototype.networkManager.neo4j.domain.enums.DeviceType;
import com.prototype.networkManager.neo4j.repository.DeviceNodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
            if(deviceType.equals(DeviceType.None)) continue;
            countDevicesByType.add(new DeviceCount(
                    deviceType.toString(),
                    deviceNodeRepository.getNumberOfDevicesByType(deviceType.toString())));
        }
        return countDevicesByType;
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
