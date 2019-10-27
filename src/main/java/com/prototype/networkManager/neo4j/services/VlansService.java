package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.exceptions.VlanNameAlreadyInDatabaseException;
import com.prototype.networkManager.neo4j.exceptions.VlanNameNotFoundException;

import java.util.List;

public interface VlansService {

    List<String> getVlansNames();

    List<String> addVlansNames(String newName) throws VlanNameAlreadyInDatabaseException;

    List<String> updateVlansNames(String oldName, String newName);

    List<String> deleteNameFromVlans(String name) throws VlanNameNotFoundException;
}
