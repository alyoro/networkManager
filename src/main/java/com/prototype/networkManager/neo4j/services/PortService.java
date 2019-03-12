package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;

public interface PortService {

    Port getPort(Long id) throws PortNotFoundException;
    Iterable<Port> getPorts();
    abstract Iterable<Port> getPorts(Long id);
    void deletePort(Long id) throws PortNotFoundException;
    void createPort(Port port, Long deviceID);
}
