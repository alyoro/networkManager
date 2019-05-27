package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Connection;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.enums.ConnectionType;
import com.prototype.networkManager.neo4j.exceptions.ConnectionCantCreatedException;
import com.prototype.networkManager.neo4j.exceptions.ConnectionNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;

import java.util.List;

public interface ConnectionService {

    Iterable<Connection> getConnections();

    Connection makeConnection(List<Port> ports, ConnectionType connectionType) throws ConnectionCantCreatedException, PortNotFoundException;

    void deleteConnection(Long connectionId) throws ConnectionNotFoundException, PortNotFoundException;
}
