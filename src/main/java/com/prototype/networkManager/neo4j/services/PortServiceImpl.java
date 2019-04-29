package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.DeviceNode;
import com.prototype.networkManager.neo4j.domain.enums.DeviceType;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.enums.PortSpeed;
import com.prototype.networkManager.neo4j.domain.enums.PortStatus;
import com.prototype.networkManager.neo4j.exceptions.*;
import com.prototype.networkManager.neo4j.repository.ConnectionRepository;
import com.prototype.networkManager.neo4j.repository.DeviceNodeRepository;
import com.prototype.networkManager.neo4j.repository.PortRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class PortServiceImpl implements PortService {

    private final PortRepository portRepository;

    private final DeviceNodeRepository deviceNodeRepository;

    private final HelperFunctions helperFunctions;

    private final ConnectionRepository connectionRepository;

    public PortServiceImpl(
            PortRepository portRepository,
            DeviceNodeRepository deviceNodeRepository,
            HelperFunctions helperFunctions, ConnectionRepository
                    connectionRepository) {
        this.portRepository = portRepository;
        this.deviceNodeRepository = deviceNodeRepository;
        this.helperFunctions = helperFunctions;
        this.connectionRepository = connectionRepository;
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
        return deviceNodeRepository.getPorts(id);
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
            port.setPortOnTheOtherElement("None");
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

    @Override
    public List<Port> createMultiplePorts(Integer numberOfPorts) {
        ArrayList<Port> ports= new ArrayList<>();
        for(int i=0; i<numberOfPorts; i++){
            ports.add(new Port(
                    i+1,
                    DeviceType.None,
                    "None",
                    PortSpeed.Ethernet1Gb,
                    PortStatus.DOWN
                    ));
        }
        return StreamSupport.stream(portRepository.saveAll(ports).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Port updatePort(Long id, Port port) throws PortNotFoundException {
        Optional<Port> portOptional = portRepository.findById(id);
        if(portOptional.isEmpty()){
            throw new PortNotFoundException("Port with id: "+id+" not found.");
        } else{
            if(portOptional.get().getConnections() == null){
                portOptional.get().setPortOnTheOtherElement("None");
                portOptional.get().setDevicePlugged(DeviceType.None);
                portOptional.get().setPortNumber(port.getPortNumber());
                portOptional.get().setPortSpeed(port.getPortSpeed());

                return portRepository.save(portOptional.get());
            } else {
                Map<String,Port> portsOfConnection = connectionRepository.getStartAndEndNode(
                        portOptional.get().getConnections().get(0).getId()).get(0);
                Port portMaster = portsOfConnection.get("portMaster");
                Port portSlave = portsOfConnection.get("portSlave");

                System.out.println(portSlave.getId());

                portOptional.get().setPortNumber(port.getPortNumber());
                portOptional.get().setPortSpeed(port.getPortSpeed());
                if(port.getId() == portMaster.getId()){
                    portSlave.setPortOnTheOtherElement(String.valueOf(port.getPortNumber()));
                    portSlave = portRepository.save(portSlave);

                    System.out.println(portSlave.toString());
                }
                return portRepository.save(portOptional.get());
            }
        }
    }

    @Override
    public Port changeStatusPort(Long id) throws PortNotFoundException, CantChangePortStatusException {
        Optional<Port> portOptional = portRepository.findById(id);
        if(portOptional.isEmpty()){
            throw new PortNotFoundException("Port with id: "+id+" not found.");
        } else{
            if(portOptional.get().getConnections() == null){
                portOptional.get().setDevicePlugged(DeviceType.None);
                portOptional.get().setPortOnTheOtherElement("None");
                portOptional.get().setPortStatus(changePortStatus(portOptional.get().getPortStatus()));

                return portRepository.save(portOptional.get());
            } else{
                throw new CantChangePortStatusException("Can't change port status because of active connection in it");
            }
        }
    }

    private PortStatus changePortStatus(PortStatus portStatus){
        if(portStatus == PortStatus.UP){
            return PortStatus.DOWN;
        } else {
            return PortStatus.UP;
        }
    }
}
