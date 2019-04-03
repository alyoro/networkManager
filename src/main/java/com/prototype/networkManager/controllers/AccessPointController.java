package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.AccessPoint;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.AccessPointNotFoundException;
import com.prototype.networkManager.neo4j.services.AccessPointService;
import com.prototype.networkManager.neo4j.services.PortService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

public class AccessPointController implements PortController {

    private final AccessPointService accessPointService;

    private final PortService portService;

    public AccessPointController(AccessPointService accessPointService, PortService portService) {
        this.accessPointService = accessPointService;
        this.portService = portService;
    }

    @GetMapping("/api/accesspoints")
    Iterable<AccessPoint> getAccessPoints(){
        return accessPointService.getAccessPoints();
    }

    @GetMapping("/api/accesspoints/{id}")
    AccessPoint getAccesPoint(@PathVariable Long id){
        try{
            return accessPointService.getAccessPoint(id);
        } catch(AccessPointNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/api/accesspoints")
    @ResponseStatus(HttpStatus.CREATED)
    AccessPoint createAccessPoint(@RequestBody AccessPoint patchPanel){
        return accessPointService.createAccessPoint(patchPanel);
    }

    @DeleteMapping("/api/accesspoints/{id}")
    void deleteAccessPoint(@PathVariable Long id){
        try{
            accessPointService.deleteAccessServer(id);
        }catch(AccessPointNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //-------------------- PortController --------------------

    @Override
    public PortService getPortService() {
        return portService;
    }

    @Override
    @GetMapping("/api/accesspoints/{id}/ports")
    public Iterable<Port> getPorts(@PathVariable Long id) {
        return PortController.super.getPorts(id);
    }

    @Override
    @GetMapping("/api/accesspoints/{id}/ports/{portId}")
    public Port getPort(@PathVariable Long portId) {
        return PortController.super.getPort(portId);
    }

    @Override
    @DeleteMapping("/api/accesspoints/{id}/ports/{portId}")
    public void deletePort(@PathVariable Long portId) {
        PortController.super.deletePort(portId);
    }

    @Override
    @PostMapping("/api/accesspoints/{id}/ports")
    @ResponseStatus(HttpStatus.CREATED)
    public Port createPort(@PathVariable Long id, @RequestBody Port port) {
        return PortController.super.createPort(id, port);
    }
}
