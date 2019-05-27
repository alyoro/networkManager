package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.None;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.Server;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.ServerNotFoundException;
import com.prototype.networkManager.neo4j.services.PortService;
import com.prototype.networkManager.neo4j.services.ServerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ServerController implements PortController {

    private final ServerService serverService;

    private final PortService portService;

    public ServerController(ServerService serverService, PortService portService) {
        this.serverService = serverService;
        this.portService = portService;
    }

    @GetMapping("/api/servers")
    @ResponseStatus(HttpStatus.OK)
    Iterable<Server> getServers() {
        return serverService.getServers();
    }

    @GetMapping("/api/servers/{id}")
    @ResponseStatus(HttpStatus.OK)
    Server getServer(@PathVariable Long id) {
        try {
            return serverService.getServer(id);
        } catch (ServerNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/api/servers")
    @ResponseStatus(HttpStatus.CREATED)
    Server createServer(@RequestBody Server server) {
        return serverService.createServer(server);
    }

    @DeleteMapping("/api/servers/{id}")
    @ResponseStatus(HttpStatus.OK)
    None deleteServer(@PathVariable Long id) {
        try {
            serverService.deleteServer(id);
            return new None();
        } catch (ServerNotFoundException | PortNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/api/servers/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    Server updateServer(@PathVariable Long id, @RequestBody Server server) {
        try {
            return serverService.updateServer(id, server);
        } catch (ServerNotFoundException e) {
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
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Port> getPorts(@PathVariable Long id) {
        return PortController.super.getPorts(id);
    }

    @Override
    @GetMapping("/api/servers/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port getPort(@PathVariable Long portId) {
        return PortController.super.getPort(portId);
    }

    @Override
    @DeleteMapping("/api/servers/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public None deletePort(@PathVariable Long portId) {
        return PortController.super.deletePort(portId);
    }

    @Override
    @PostMapping("/api/servers/{id}/ports")
    @ResponseStatus(HttpStatus.CREATED)
    public Port createPort(@PathVariable Long id, @RequestBody Port port) {
        return PortController.super.createPort(id, port);
    }

    @Override
    @PutMapping("/api/servers/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port updatePort(@PathVariable Long portId, @RequestBody Port port) {
        return PortController.super.updatePort(portId, port);
    }

    @Override
    @PatchMapping("/api/servers/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port changeStatusPort(@PathVariable Long portId) {
        return PortController.super.changeStatusPort(portId);
    }
}
