package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Connection;
import com.prototype.networkManager.neo4j.domain.enums.DeviceType;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.ConnectionCantCreatedException;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.repository.ConnectionRepository;
import com.prototype.networkManager.neo4j.repository.DeviceNodeRepository;
import com.prototype.networkManager.neo4j.repository.PortRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;

    private final PortRepository portRepository;

    private final DeviceNodeRepository deviceNodeRepository;


    public ConnectionServiceImpl(ConnectionRepository connectionRepository, PortRepository portRepository, DeviceNodeRepository deviceNodeRepository) {
        this.connectionRepository = connectionRepository;
        this.portRepository = portRepository;
        this.deviceNodeRepository = deviceNodeRepository;
    }

    @Override
    public Iterable<Connection> getConnections() {
        return connectionRepository.findAll();
    }

    @Override
    public Connection addConnection(List<Port> ports) throws ConnectionCantCreatedException, PortNotFoundException{
        List<String> vlans = new ArrayList<>();
        vlans.add("test"); // TODO making vlans
        vlans.add("test2");

        Optional<Port> portMaster = portRepository.findById(ports.get(0).getId());
        Optional<Port> portSlave = portRepository.findById(ports.get(1).getId());

        if(portMaster.isPresent() && portSlave.isPresent()){
            if(portMaster.get().getConnections()== null && portSlave.get().getConnections() == null){
                Connection newConnection = new Connection();
                portSlave.get().setPortOnTheUpperElement(Integer.toString(portMaster.get().getPortNumber()));
                portSlave.get().setDevicePlugged(DeviceType.valueOf(deviceNodeRepository.getDeviceNodeTypeByIdOfPort(
                        portMaster.get().getId()).get(0).get("DeviceType").toString()));
                newConnection.setStartNode(portSlave.get());
                newConnection.setEndNode(portMaster.get());
                newConnection.setVlans(vlans);
                return connectionRepository.save(newConnection);
            } else {
                throw new ConnectionCantCreatedException("Port/ports already occupied.");
            }
        } else {
            throw new PortNotFoundException("Port with id: "+ports.get(0).getId()+" or id: "+ports.get(1).getId()+" not found.");
        }
    }

}
