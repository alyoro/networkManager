package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Connection;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.enums.DeviceType;
import com.prototype.networkManager.neo4j.exceptions.ConnectionCantCreatedException;
import com.prototype.networkManager.neo4j.exceptions.ConnectionNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.repository.ConnectionRepository;
import com.prototype.networkManager.neo4j.repository.DeviceNodeRepository;
import com.prototype.networkManager.neo4j.repository.PortRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Connection makeConnection(List<Port> ports) throws ConnectionCantCreatedException, PortNotFoundException {

        Optional<Port> portMaster = portRepository.findById(ports.get(0).getId());
        Optional<Port> portSlave = portRepository.findById(ports.get(1).getId());

        if (portMaster.isPresent() && portSlave.isPresent()) {
            if (portMaster.get().getConnections() == null && portSlave.get().getConnections() == null) {
                Connection newConnection = new Connection();

                portSlave.get().setPortOnTheOtherElement(Integer.toString(portMaster.get().getPortNumber()));
                portSlave.get().setDevicePlugged(
                        deviceNodeRepository.getDeviceNodeByIdOfPort(
                                portMaster.get().getId()).get().getIdentifier());
                portMaster.get().setPortOnTheOtherElement(Integer.toString(portSlave.get().getPortNumber()));
                portMaster.get().setDevicePlugged(
                        deviceNodeRepository.getDeviceNodeByIdOfPort(
                                        portSlave.get().getId()).get().getIdentifier());

                newConnection.setStartNode(portSlave.get());
                newConnection.setEndNode(portMaster.get());
                newConnection.setPortIdStart(portSlave.get().getId());
                newConnection.setPortIdEnd(portMaster.get().getId());
                return connectionRepository.save(newConnection);
            } else {
                throw new ConnectionCantCreatedException("Port/ports already occupied.");
            }
        } else {
            throw new PortNotFoundException("Port with id: " + ports.get(0).getId() + " or id: " + ports.get(1).getId() + " not found.");
        }
    }

    @Override
    public void deleteConnection(Long connectionId) throws ConnectionNotFoundException, PortNotFoundException {
        Optional<Connection> connectionOptional = connectionRepository.findById(connectionId);

        if (connectionOptional.isPresent()) {
            Optional<Port> portStart = portRepository.findById(connectionOptional.get().getPortIdStart());
            Optional<Port> portEnd = portRepository.findById(connectionOptional.get().getPortIdEnd());

            if (portStart.isPresent()) {
                portStart.get().setPortOnTheOtherElement("None");
                portStart.get().setDevicePlugged("None");
                portRepository.save(portStart.get());
            } else {
                throw new PortNotFoundException("Port with id: " + connectionOptional.get().getPortIdStart() + " not found.");
            }

            if (portEnd.isPresent()) {
                portEnd.get().setPortOnTheOtherElement("None");
                portEnd.get().setDevicePlugged("None");
                portRepository.save(portEnd.get());
            } else {
                throw new PortNotFoundException("Port with id: " + connectionOptional.get().getPortIdEnd() + " not found.");
            }

            connectionRepository.delete(connectionOptional.get());
        } else {
            throw new ConnectionNotFoundException("Connection with id: " + connectionId + " not found.");
        }
    }
}
