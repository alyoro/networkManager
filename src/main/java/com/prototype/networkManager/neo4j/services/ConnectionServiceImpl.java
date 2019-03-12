package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Connection;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.repository.ConnectionRepository;
import com.prototype.networkManager.neo4j.repository.PortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    @Autowired
    ConnectionRepository connectionRepository;

    @Autowired
    PortRepository portRepository;

    @Override
    public Iterable<Connection> getConnections() {
        return connectionRepository.findAll();
    }

    @Override
    public Connection addConnection(List<Port> ports) {
        Connection c = new Connection(portRepository.findById(ports.get(0).getId()).get(), portRepository.findById(ports.get(1).getId()).get());
        return connectionRepository.save(c);

    }


}
