package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Server;
import com.prototype.networkManager.neo4j.exceptions.ServerNotFoundException;
import com.prototype.networkManager.neo4j.repository.ServerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;

    public ServerServiceImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public Server getServer(Long id) throws ServerNotFoundException {
        Optional<Server> serverOptional = serverRepository.findById(id);
        if(serverOptional.isPresent()){
            return serverOptional.get();
        } else{
            throw new ServerNotFoundException("Server with this id: "+id+" not found");
        }
    }

    @Override
    public Iterable<Server> getServers() {
        return serverRepository.findAll(2);
    }

    @Override
    public void deleteServer(Long id) throws ServerNotFoundException {
        if(serverRepository.findById(id).isPresent()){
            serverRepository.deleteById(id);
        }else{
            throw new ServerNotFoundException("Server with this id: "+id+" not found");
        }
    }

    @Override
    public Server createServer(Server server) {
        return serverRepository.save(server);
    }
}
