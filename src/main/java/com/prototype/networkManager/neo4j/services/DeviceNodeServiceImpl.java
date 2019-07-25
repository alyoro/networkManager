package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.DeviceNode;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.enums.DeviceType;
import com.prototype.networkManager.neo4j.exceptions.DeviceNotFoundException;
import com.prototype.networkManager.neo4j.repository.DeviceNodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class DeviceNodeServiceImpl implements DeviceNodeService {

    @Autowired
    private final DeviceNodeRepository deviceNodeRepository;

    public DeviceNodeServiceImpl(DeviceNodeRepository deviceNodeRepository) {
        this.deviceNodeRepository = deviceNodeRepository;
    }

    @Override
    public DeviceNode getDeviceByPortId(Long portId) throws DeviceNotFoundException{
        Optional<DeviceNode> deviceNodeOptional = deviceNodeRepository.getDeviceNodeByIdOfPort(portId);
        if(deviceNodeOptional.isPresent()){
            return deviceNodeOptional.get();
        } else {
            log.warn("Cant find device with port of id: "+portId);
            throw new DeviceNotFoundException("Cant find device with port of id: "+portId);
        }
    }

    @Override
    public DeviceType getDeviceNodeTypeByIdOfPort(Long portId) throws DeviceNotFoundException {
        List<Map<String,Object>> list = deviceNodeRepository.getDeviceNodeTypeByIdOfPort(portId);
        return  DeviceType.valueOf(list.get(0).get("DeviceType").toString());
    }

    @Override
    public DeviceNode getDeviceNodeByIdOfPort(Long portId) throws DeviceNotFoundException {
        Optional<DeviceNode> deviceNodeOptional = deviceNodeRepository.getDeviceNodeByIdOfPort(portId);
        if(deviceNodeOptional.isPresent()){
            return deviceNodeOptional.get();
        } else {
            log.warn("Cant find device with port of id: "+portId);
            throw new DeviceNotFoundException("Cant find device with port of id: "+portId);
        }

    }

    @Override
    public Integer getNumberOfDevicesByType(String deviceType) {
        return deviceNodeRepository.getNumberOfDevicesByType(deviceType);
    }

    @Override
    public Iterable<Port> getDevicePorts(Long deviceId) {
        return deviceNodeRepository.getPorts(deviceId);
    }

    @Override
    public DeviceNode getDeviceNodeById(Long id, int depth) throws DeviceNotFoundException{
        Optional<DeviceNode> deviceNodeOptional = deviceNodeRepository.findById(id, depth);
        if(deviceNodeOptional.isPresent()){
            return deviceNodeOptional.get();
        } else {
            log.warn("Cant find device with id: "+id);
            throw new DeviceNotFoundException("Cant find device with id: "+id);
        }
    }

    @Override
    public DeviceNode save(DeviceNode deviceNode) {
        return deviceNodeRepository.save(deviceNode);
    }
}
