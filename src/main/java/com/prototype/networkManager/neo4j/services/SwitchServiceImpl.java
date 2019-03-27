package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Switch;
import com.prototype.networkManager.neo4j.exceptions.SwitchNotFoundException;
import com.prototype.networkManager.neo4j.repository.SwitchRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SwitchServiceImpl implements SwitchService {

    private final SwitchRepository switchRepository;

    public SwitchServiceImpl(SwitchRepository switchRepository){
        this.switchRepository = switchRepository;
    }

    @Override
    public Switch getSwitch(Long id) throws SwitchNotFoundException {
        Optional<Switch> switchOptional = switchRepository.findById(id);
        if(switchOptional.isPresent()){
            return switchOptional.get();
        }else{
            throw new SwitchNotFoundException("Switch wid id: "+id+" not found.");
        }
    }

    @Override
    public Iterable<Switch> getSwitches() {
        return switchRepository.findAll(2);
    }

    @Override
    public void deleteSwitch(Long id) throws SwitchNotFoundException {
        if(switchRepository.findById(id).isPresent()){
            switchRepository.deleteById(id);
        }else {
            throw new SwitchNotFoundException("Switch with id: "+id+" not found.");
        }
    }

    @Override
    public Switch createSwitch(Switch switchDevice) {
        return switchRepository.save(switchDevice);
    }
}