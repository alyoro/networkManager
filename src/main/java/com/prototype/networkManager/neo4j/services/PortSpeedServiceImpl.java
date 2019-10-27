package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.exceptions.PortSpeedNameAlreadyInDatabaseException;
import com.prototype.networkManager.neo4j.exceptions.PortSpeedNameNotFoundException;
import com.prototype.networkManager.neo4j.repository.PortSpeedRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PortSpeedServiceImpl implements PortSpeedService {

    private PortSpeedRepository portSpeedRepository;

    public PortSpeedServiceImpl(PortSpeedRepository portSpeedRepository) {
        this.portSpeedRepository = portSpeedRepository;
    }

    @Override
    public List<String> getPortSpeedNames() {
        return portSpeedRepository.getPortSpeedTypes().get().getNames();
    }

    @Override
    public List<String> addPortSpeedNames(String newName) throws PortSpeedNameAlreadyInDatabaseException{
        List<String> listOfNames = portSpeedRepository.getPortSpeedTypes().get().getNames();
        if(!listOfNames.contains(newName)){
            listOfNames.add(newName);
        } else {
            throw new PortSpeedNameAlreadyInDatabaseException("PortSpeedName: ("+ newName + ") already in database.");
        }
        portSpeedRepository.savePortSpeedTypes(listOfNames);
        return portSpeedRepository.getPortSpeedTypes().get().getNames();
    }

    @Override
    public List<String> updatePortSpeedNames(String oldName, String newName){
        List<String> listOfNames = portSpeedRepository.getPortSpeedTypes().get().getNames();
        if(!listOfNames.contains(oldName)){
            listOfNames.add(newName);
        } else {
            listOfNames.set(listOfNames.lastIndexOf(oldName), newName);
        }
        portSpeedRepository.savePortSpeedTypes(listOfNames);
        return portSpeedRepository.getPortSpeedTypes().get().getNames();
    }


    @Override
    public List<String> deleteNameFromPortSpeed(String deletedName) throws PortSpeedNameNotFoundException {
        List<String> listOfNames = portSpeedRepository.getPortSpeedTypes().get().getNames();
        if(listOfNames.contains(deletedName)){
            listOfNames.remove(deletedName);
        } else {
            throw new PortSpeedNameNotFoundException("PortSpeedName: ("+ deletedName + ") not found in database.");
        }
        portSpeedRepository.savePortSpeedTypes(listOfNames);
        return portSpeedRepository.getPortSpeedTypes().get().getNames();
    }
}
