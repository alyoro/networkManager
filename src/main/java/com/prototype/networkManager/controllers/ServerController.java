package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.None;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.Server;
import com.prototype.networkManager.neo4j.exceptions.ServerNotFoundException;
import com.prototype.networkManager.neo4j.services.PortService;
import com.prototype.networkManager.neo4j.services.ServerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ServerController implements PortController{

    private final ServerService serverService;

    private final PortService portService;

    public ServerController(ServerService serverService, PortService portService) {
        this.serverService = serverService;
        this.portService = portService;
    }

    @GetMapping("/api/servers")
    Iterable<Server> getServers(){
        return serverService.getServers();
    }

    @GetMapping("/api/servers/{id}")
    Server getServer(@PathVariable("id") Long id){
        try{
            return serverService.getServer(id);
        } catch(ServerNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/api/servers")
    @ResponseStatus(HttpStatus.CREATED)
    Server createServer(@RequestBody Server patchPanel){
        return serverService.createServer(patchPanel);
    }

    @DeleteMapping("/api/servers/{id}")
    void deleteServer(@PathVariable("id") Long id){
        try{
            serverService.deleteServer(id);
        }catch(ServerNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //-------------------- PortController --------------------

    @Override
    public PortService getPortService() {
        return portService;
    }

    @Override
    @GetMapping("/api/servers/{id}/ports")
    public Iterable<Port> getPorts(@PathVariable("id") Long id) {
        return PortController.super.getPorts(id);
    }

    @Override
    @GetMapping("/api/servers/{id}/ports/{portId}")
    public Port getPort(@PathVariable("portId") Long portId) {
        return PortController.super.getPort(portId);
    }

    @Override
    @DeleteMapping("/api/servers/{id}/ports/{portId}")
    public None deletePort(@PathVariable("portId") Long portId) {
        return PortController.super.deletePort(portId);
    }

    @Override
    @PostMapping("/api/servers/{id}/ports")
    @ResponseStatus(HttpStatus.CREATED)
    public Port createPort(@PathVariable("id") Long id, @RequestBody Port port) {
        return PortController.super.createPort(id, port);
    }
}
