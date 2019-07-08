package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.exceptions.VlanNameAlreadyInDatabaseException;
import com.prototype.networkManager.neo4j.exceptions.VlanNameNotFoundException;

import java.util.List;

public interface VlansService {

    List<String> getVlansNames();

    List<String> updateVlansNames(String newName) throws VlanNameAlreadyInDatabaseException;

    List<String> deleteNameFromVlans(String name) throws VlanNameNotFoundException;
}
