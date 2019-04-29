package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.Switch;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.SwitchNotFoundException;
import com.prototype.networkManager.neo4j.repository.PortRepository;
import com.prototype.networkManager.neo4j.repository.SwitchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class SwitchServiceImpl implements SwitchService {

    private final SwitchRepository switchRepository;

    private final PortService portService;

    public SwitchServiceImpl(SwitchRepository switchRepository, PortService portService) {
        this.switchRepository = switchRepository;
        this.portService = portService;
    }

    @Override
    public Switch getSwitch(Long id) throws SwitchNotFoundException {
        Optional<Switch> switchOptional = switchRepository.findById(id);
        if(switchOptional.isPresent()){
            return switchOptional.get();
        }else{
            throw new SwitchNotFoundException("Switch with id: "+id+" not found.");
        }
    }

    @Override
    public Iterable<Switch> getSwitches() {
        return switchRepository.findAll(2);
    }

    @Override
    public void deleteSwitch(Long id) throws SwitchNotFoundException, PortNotFoundException {
        Optional<Switch> switchOptional = switchRepository.findById(id);
        if(switchOptional.isPresent()){
            if(!switchOptional.get().getPorts().isEmpty()){
                for(Port port: switchOptional.get().getPorts()){
                    portService.deletePort(port.getId());
                }
            }
            switchRepository.deleteById(id);
        } else {
            throw new SwitchNotFoundException("Switch with id: "+id+" not found.");
        }
    }

    @Override
    public Switch createSwitch(Switch switchDevice) {
        switchDevice.setPorts(portService.createMultiplePorts(switchDevice.getNumberOfPorts()));
        return switchRepository.save(switchDevice);
    }
}
