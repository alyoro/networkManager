package com.prototype.networkManager.neo4j.services;


import com.prototype.networkManager.neo4j.domain.DeviceNode;
import com.prototype.networkManager.neo4j.domain.DeviceType;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.*;
import com.prototype.networkManager.neo4j.repository.DeviceNodeRepository;
import com.prototype.networkManager.neo4j.repository.PortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PortServiceImpl implements PortService {

    private final PortRepository portRepository;

    private final DeviceNodeRepository deviceNodeRepository;

    private final HelperFunctions helperFunctions;

    @Autowired
    public PortServiceImpl(PortRepository portRepository, DeviceNodeRepository deviceNodeRepository, HelperFunctions helperFunctions) {
        this.portRepository = portRepository;
        this.deviceNodeRepository = deviceNodeRepository;
        this.helperFunctions = helperFunctions;
    }

    @Override
    public Port getPort(Long id) throws PortNotFoundException{
        Optional<Port> port = portRepository.findById(id);
        if(port.isPresent()){
            return port.get();
        }else{
            throw new PortNotFoundException("Port with id: "+id+" not found.");
        }
    }

    @Override
    public Iterable<Port> getPorts() {
        return portRepository.findAll();
    }

    @Override
    public Iterable<Port> getPorts(Long id) {
        return portRepository.getPorts(id);
    }

    @Override
    public void deletePort(Long id) throws PortNotFoundException{
        if(portRepository.findById(id).isPresent()){
            portRepository.deleteById(id);
        }else{
            throw new PortNotFoundException("Port with id: "+id+" not found.");
        }
    }

    @Override
    public Port createPort(Long id, Port port)
            throws DeviceNotFoundException, MaximumPortNumberReachedException, PortNumberAlreadyInListException {
        Optional<DeviceNode> node = deviceNodeRepository.findById(id);
        if(node.isEmpty()) {
            throw new DeviceNotFoundException("Device with id: "+id+" not found.");
        }
        else {
            List<Port> ports = node.get().getPorts();
            port.setDevicePlugged(DeviceType.None);
            port.setPortOnTheUpperElement("None");
            if(ports != null){
                if(node.get().getNumberOfPorts() == ports.size()){
                    throw new MaximumPortNumberReachedException("Cant put more ports in this device");
                }
                if(helperFunctions.arePortNumberListUnique(ports, port.getPortNumber())){
                    ports.add(port);
                } else{
                    throw new PortNumberAlreadyInListException("Port with this number already added to device");
                }
            } else{
                if(node.get().getNumberOfPorts() > 0) {
                    ports = new ArrayList<>();
                    ports.add(port);
                } else {
                    throw new MaximumPortNumberReachedException("Cant put more ports in this device");
                }
            }
            node.get().setPorts(ports);
            deviceNodeRepository.save(node.get());
            return port;
        }
    }
}
