package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.services.PortService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

public interface PortController {

    PortService getPortService();

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping(path = "/ports")
    default Iterable<Port> getPorts(){
        return getPortService().getPorts();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping(path = "/ports/{id:\\d+}")
    default Port getPort(@PathVariable("id") Long id){
        try{
            return getPortService().getPort(id);
        }catch (PortNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @DeleteMapping(path = "/ports/{id}")
    default void deletePort(@PathVariable("id") Long id){
        try{
            getPortService().deletePort(id);
        }catch(PortNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    void createPort(@PathVariable("id") Long id, @RequestBody Port port);
}
