package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.exceptions.PortSpeedNameAlreadyInDatabaseException;
import com.prototype.networkManager.neo4j.exceptions.PortSpeedNameNotFoundException;

import java.util.List;

public interface PortSpeedService {

    List<String> getPortSpeedNames();

    List<String> updatePortSpeedNames(String newName) throws PortSpeedNameAlreadyInDatabaseException;

    List<String> deleteNameFromPortSpeed(String name) throws PortSpeedNameNotFoundException;

}
