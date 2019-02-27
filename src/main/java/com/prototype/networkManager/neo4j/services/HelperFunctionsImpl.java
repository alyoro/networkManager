package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Port;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelperFunctionsImpl implements HelperFuncitons {

    @Override
    public boolean arePortNumberListUnique(List<Port> ports, int newPortNumber) {

        boolean isUnique = true;

        for(Port port: ports){
            if(port.getPortNumber() == newPortNumber){
                isUnique = false;
                return isUnique;
            }
        }
        return isUnique;
    }
}
