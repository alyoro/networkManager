package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Connection;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.enums.ConnectionType;
import com.prototype.networkManager.neo4j.exceptions.ConnectionCantCreatedException;
import com.prototype.networkManager.neo4j.exceptions.ConnectionNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.repository.ConnectionRepository;
import com.prototype.networkManager.neo4j.repository.PortRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Transactional
@Slf4j
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;

    private final PortRepository portRepository;

    private final DeviceNodeService deviceNodeService;


    public ConnectionServiceImpl(ConnectionRepository connectionRepository, PortRepository portRepository, DeviceNodeService deviceNodeService) {
        this.connectionRepository = connectionRepository;
        this.portRepository = portRepository;
        this.deviceNodeService = deviceNodeService;
    }

    @Override
    public Iterable<Connection> getConnections() {
        return connectionRepository.findAll();
    }

    @Override
    public Map<String, Port> getStartAndEndNode(Long connectionId) {
        return connectionRepository.getStartAndEndNode(connectionId).get(0);
    }


    private boolean ifConnectionsAreSameType(List<Connection> connections, ConnectionType connectionType) {
        AtomicBoolean result = new AtomicBoolean(false);
        connections.forEach(c -> {
            if (c.getConnectionType() == connectionType) {
                result.set(true);
            }
        });
        return result.get();
    }

    @Override
    public Connection makeConnection(List<Port> ports, ConnectionType connectionType) throws ConnectionCantCreatedException, PortNotFoundException {

        Optional<Port> portMaster = portRepository.findById(ports.get(0).getId());
        Optional<Port> portSlave = portRepository.findById(ports.get(1).getId());

        if (portMaster.isPresent() && portSlave.isPresent()) {
            if ((portMaster.get().getConnections()) == null || this.ifConnectionsAreSameType(portMaster.get().getConnections(), connectionType)
                    || (portSlave.get().getConnections() == null || this.ifConnectionsAreSameType(portSlave.get().getConnections(), connectionType))) {
                try {
                    Connection newConnection = new Connection();
                    newConnection.setConnectionType(connectionType);

                    if (connectionType.equals(ConnectionType.SOCKET)) {
                        portSlave.get().setPortOnTheOtherElement(Integer.toString(portMaster.get().getPortNumber()));
                        portSlave.get().setDevicePlugged(
                                deviceNodeService.getDeviceByPortId(portMaster.get().getId()).getIdentifier());

                        portMaster.get().setPortOnTheOtherElement(Integer.toString(portSlave.get().getPortNumber()));
                        portMaster.get().setDevicePlugged(
                                deviceNodeService.getDeviceByPortId(portSlave.get().getId()).getIdentifier());
                    }
                    newConnection.setStartNode(portSlave.get());
                    newConnection.setEndNode(portMaster.get());
                    newConnection.setPortIdStart(portSlave.get().getId());
                    newConnection.setPortIdEnd(portMaster.get().getId());
                    return connectionRepository.save(newConnection);
                } catch (Exception e) {
                    throw new ConnectionCantCreatedException("ConnectionService: " + e.getMessage());
                }
            } else {
                throw new ConnectionCantCreatedException("Port/ports already occupied.");
            }
        } else {
            throw new PortNotFoundException("Port with id: " + ports.get(0).getId() + " or port with id: " + ports.get(1).getId() + " not found.");
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
