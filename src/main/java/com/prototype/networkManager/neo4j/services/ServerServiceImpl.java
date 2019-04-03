package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.Server;
import com.prototype.networkManager.neo4j.exceptions.ServerNotFoundException;
import com.prototype.networkManager.neo4j.repository.PortRepository;
import com.prototype.networkManager.neo4j.repository.ServerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;

    private final PortRepository portRepository;

    public ServerServiceImpl(ServerRepository serverRepository, PortRepository portRepository) {
        this.serverRepository = serverRepository;
        this.portRepository = portRepository;
    }

    @Override
    public Server getServer(Long id) throws ServerNotFoundException {
        Optional<Server> serverOptional = serverRepository.findById(id);
        if(serverOptional.isPresent()){
            return serverOptional.get();
        } else{
            throw new ServerNotFoundException("Server with id: "+id+" not found");
        }
    }

    @Override
    public Iterable<Server> getServers() {
        return serverRepository.findAll(2);
    }

    @Override
    public void deleteServer(Long id) throws ServerNotFoundException {
        Optional<Server> serverOptional = serverRepository.findById(id);
        if(serverOptional.isPresent()) {
            if (!serverOptional.get().getPorts().isEmpty()) {
                for (Port port : serverOptional.get().getPorts()) {
                    portRepository.delete(port);
                }
            }
            serverRepository.deleteById(id);
        } else {
            throw new ServerNotFoundException("Server with id: "+id+" not found");
        }
    }

    @Override
    public Server createServer(Server server) {
        return serverRepository.save(server);
    }
}
