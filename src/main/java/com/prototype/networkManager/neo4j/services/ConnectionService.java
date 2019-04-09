package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Connection;
import com.prototype.networkManager.neo4j.domain.Port;

import java.util.List;

public interface ConnectionService {

    Iterable<Connection> getConnections();
    Connection addConnection(List<Port> ports);
}
