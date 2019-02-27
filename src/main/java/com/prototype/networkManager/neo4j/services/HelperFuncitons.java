package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Port;

import java.util.List;

public interface HelperFuncitons {

    boolean arePortNumberListUnique(List<Port> ports, int newPortNumber);

}
