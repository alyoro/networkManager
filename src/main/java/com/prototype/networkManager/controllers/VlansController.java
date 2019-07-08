package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.exceptions.VlanNameAlreadyInDatabaseException;
import com.prototype.networkManager.neo4j.exceptions.VlanNameNotFoundException;
import com.prototype.networkManager.neo4j.services.VlansService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class VlansController {

    private final VlansService vlansService;

    public VlansController(VlansService vlansService) {
        this.vlansService = vlansService;
    }

    @GetMapping("/api/vlans")
    List<String> getPortSpeedNames(){
        return vlansService.getVlansNames();
    }

    @PatchMapping("/api/vlans/{name}")
    List<String> patchPortSpeedNames(@PathVariable String name) {
        try {
            return vlansService.updateVlansNames(name);
        } catch (VlanNameAlreadyInDatabaseException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/api/vlans/{name}")
    List<String> deletePortSpeedNames(@PathVariable String name) {
        try {
            return vlansService.deleteNameFromVlans(name);
        } catch (VlanNameNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
