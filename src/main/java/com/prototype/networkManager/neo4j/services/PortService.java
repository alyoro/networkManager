package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.*;

import java.util.List;

public interface PortService {

    Port getPort(Long id) throws PortNotFoundException;

    Iterable<Port> getPorts();

    Iterable<Port> getPorts(Long id);

    void deletePort(Long id) throws PortNotFoundException;

    Port createPort(Long deviceID, Port port) throws DeviceNotFoundException, MaximumPortNumberReachedException, PortNumberAlreadyInListException;

    List<Port> createMultiplePorts(Integer numberOfPorts);

    Port updatePort(Long id, Port port) throws PortNotFoundException;

    Port changeStatusPort(Long id) throws PortNotFoundException, CantChangePortStatusException;

}
