package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.repository.PortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PortServiceImpl implements PortService {

    private final
    PortRepository portRepository;

    @Autowired
    public PortServiceImpl(PortRepository portRepository) {
        this.portRepository = portRepository;
    }

    @Override
    public Port getPort(Long id) throws PortNotFoundException{
        Optional<Port> port = portRepository.findById(id);
        if(port.isPresent()){
            return port.get();
        }else{
            throw new PortNotFoundException("Port with id: "+id+" not found.");
        }
    }

    @Override
    public Iterable<Port> getPorts() {
        return portRepository.findAll();
    }

    @Override
    public Iterable<Port> getPorts(Long id) {
        return portRepository.getPorts(id);
    }

    @Override
    public void deletePort(Long id) throws PortNotFoundException{
        if(portRepository.findById(id).isPresent()){
            portRepository.deleteById(id);
        }else{
            throw new PortNotFoundException("Port with id: "+id+" not found.");
        }
    }

    @Override
    public void createPort(Port port, Long deviceID) {

    }
}
