package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.AccessPoint;
import com.prototype.networkManager.neo4j.exceptions.AccessPointNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;

public interface AccessPointService {
    AccessPoint getAccessPoint(Long id) throws AccessPointNotFoundException;

    Iterable<AccessPoint> getAccessPoints();

    void deleteAccessServer(Long id) throws AccessPointNotFoundException, PortNotFoundException;

    AccessPoint createAccessPoint(AccessPoint accessPoint);

    AccessPoint updateAccessPoint(Long id, AccessPoint accessPoint) throws AccessPointNotFoundException;
}
