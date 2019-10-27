package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Connection;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.enums.ConnectionType;
import com.prototype.networkManager.neo4j.exceptions.ConnectionCantCreatedException;
import com.prototype.networkManager.neo4j.exceptions.ConnectionNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;

import java.util.List;
import java.util.Map;

public interface ConnectionService {

    Iterable<Connection> getConnections();

    Map<String, Port> getStartAndEndNode(Long connectionId);

    Connection makeConnection(List<Port> ports, ConnectionType connectionType) throws ConnectionCantCreatedException, PortNotFoundException;

    void deleteConnection(Long connectionId) throws ConnectionNotFoundException, PortNotFoundException;
}
