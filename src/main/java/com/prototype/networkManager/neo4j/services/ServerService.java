package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Server;
import com.prototype.networkManager.neo4j.exceptions.ServerNotFoundException;

public interface ServerService {
    Server getServer(Long id) throws ServerNotFoundException;
    Iterable<Server> getServers();
    void deleteServer(Long id) throws ServerNotFoundException;
    Server createServer(Server server);
}
