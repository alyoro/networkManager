package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.None;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.DeviceNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.MaximumPortNumberReachedException;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PortNumberAlreadyInListException;
import com.prototype.networkManager.neo4j.services.PortService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

public interface PortController {

    PortService getPortService();

    @GetMapping(path = "{id:\\d+}/ports")
    @ResponseStatus(HttpStatus.OK)
    default Iterable<Port> getPorts(@PathVariable Long id){
        return getPortService().getPorts(id);
    }

    @GetMapping(path = "/ports/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    default Port getPort(@PathVariable Long id){
        try{
            return getPortService().getPort(id);
        }catch (PortNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping(path = "/ports/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    default None deletePort(@PathVariable Long id){
        try{
            getPortService().deletePort(id);
            return new None();
        }catch(PortNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "{id:\\d+}/ports")
    @ResponseStatus(HttpStatus.CREATED)
    default Port createPort(@PathVariable Long id, @RequestBody Port port){
        try{
            return getPortService().createPort(id, port);
        }catch(DeviceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch(MaximumPortNumberReachedException | PortNumberAlreadyInListException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}
