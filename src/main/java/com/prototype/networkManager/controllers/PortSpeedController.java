package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.exceptions.PortSpeedNameAlreadyInDatabaseException;
import com.prototype.networkManager.neo4j.exceptions.PortSpeedNameNotFoundException;
import com.prototype.networkManager.neo4j.services.PortSpeedService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class PortSpeedController {

    private final PortSpeedService portSpeedService;

    public PortSpeedController(PortSpeedService portSpeedService) {
        this.portSpeedService = portSpeedService;
    }

    @GetMapping("/api/portspeednames")
    List<String> getPortSpeedNames(){
        return portSpeedService.getPortSpeedNames();
    }

    @PatchMapping("/api/portspeednames/{name}")
    List<String> patchPortSpeedNames(@PathVariable String name){
        try {
            return portSpeedService.updatePortSpeedNames(name);
        } catch (PortSpeedNameAlreadyInDatabaseException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/api/portspeednames/{name}")
    List<String> deletePortSpeedNames(@PathVariable String name){
        try {
            return portSpeedService.deleteNameFromPortSpeed(name);
        } catch (PortSpeedNameNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
