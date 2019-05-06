package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.Server;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.ServerNotFoundException;
import com.prototype.networkManager.neo4j.repository.ServerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;

    private final PortService portService;

    public ServerServiceImpl(ServerRepository serverRepository, PortService portService) {
        this.serverRepository = serverRepository;
        this.portService = portService;
    }

    @Override
    public Server getServer(Long id) throws ServerNotFoundException {
        Optional<Server> serverOptional = serverRepository.findById(id);
        if (serverOptional.isPresent()) {
            return serverOptional.get();
        } else {
            throw new ServerNotFoundException("Server with id: " + id + " not found");
        }
    }

    @Override
    public Iterable<Server> getServers() {
        return serverRepository.findAll(2);
    }

    @Override
    public void deleteServer(Long id) throws ServerNotFoundException, PortNotFoundException {
        Optional<Server> serverOptional = serverRepository.findById(id);
        if (serverOptional.isPresent()) {
            if (!serverOptional.get().getPorts().isEmpty()) {
                for (Port port : serverOptional.get().getPorts()) {
                    portService.deletePort(port.getId());
                }
            }
            serverRepository.deleteById(id);
        } else {
            throw new ServerNotFoundException("Server with id: " + id + " not found");
        }
    }

    @Override
    public Server createServer(Server server) {
        server.setPorts(portService.createMultiplePorts(server.getNumberOfPorts()));
        return serverRepository.save(server);
    }

    @Override
    public Server updateServer(Long id, Server server) throws ServerNotFoundException {
        Optional<Server> serverOptional = serverRepository.findById(id);
        if (serverOptional.isPresent()) {
            serverOptional.get().setLocalization(server.getLocalization());
            serverOptional.get().setIp(server.getIp());

            return serverRepository.save(serverOptional.get());
        } else {
            throw new ServerNotFoundException("Server with id: " + id + " not found.");
        }

    }
}
