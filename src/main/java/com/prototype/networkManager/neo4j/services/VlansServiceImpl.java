package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.exceptions.VlanNameAlreadyInDatabaseException;
import com.prototype.networkManager.neo4j.exceptions.VlanNameNotFoundException;
import com.prototype.networkManager.neo4j.repository.VlansRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VlansServiceImpl implements VlansService {

    private VlansRepository vlansRepository;

    public VlansServiceImpl(VlansRepository vlansRepository) {
        this.vlansRepository = vlansRepository;
    }

    @Override
    public List<String> getVlansNames() {
        return vlansRepository.getVlansTypes().get().getNames();
    }

    @Override
    public List<String> addVlansNames(String newName) throws VlanNameAlreadyInDatabaseException {
        List<String> listOfNames = vlansRepository.getVlansTypes().get().getNames();
        if(!listOfNames.contains(newName)){
            listOfNames.add(newName);
        } else {
            throw new VlanNameAlreadyInDatabaseException("VlanName: ("+newName+") already in database.");
        }
        vlansRepository.saveVlansTypes(listOfNames);
        return vlansRepository.getVlansTypes().get().getNames();
    }

    @Override
    public List<String> updateVlansNames(String oldName, String newName) {
        List<String> listOfNames = vlansRepository.getVlansTypes().get().getNames();
        if (!listOfNames.contains(oldName)) {
            listOfNames.add(newName);
        } else {
            listOfNames.set(listOfNames.lastIndexOf(oldName), newName);
        }
        vlansRepository.saveVlansTypes(listOfNames);
        return vlansRepository.getVlansTypes().get().getNames();
    }

    @Override
    public List<String> deleteNameFromVlans(String deletedName) throws VlanNameNotFoundException {
        List<String> listOfNames = vlansRepository.getVlansTypes().get().getNames();
        if(listOfNames.contains(deletedName)){
            listOfNames.remove(deletedName);
        } else {
            throw new VlanNameNotFoundException("VlanName: ("+ deletedName + ") not found in database.");
        }
        vlansRepository.saveVlansTypes(listOfNames);
        return vlansRepository.getVlansTypes().get().getNames();
    }
}
