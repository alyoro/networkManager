package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.DeviceNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.MaximumPortNumberReachedException;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PortNumberAlreadyInListException;

public interface PortService {

    Port getPort(Long id) throws PortNotFoundException;
    Iterable<Port> getPorts();
    Iterable<Port> getPorts(Long id);
    void deletePort(Long id) throws PortNotFoundException;
    Port createPort(Long deviceID, Port port) throws DeviceNotFoundException, MaximumPortNumberReachedException, PortNumberAlreadyInListException;
}
