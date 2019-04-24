package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.None;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.services.PortService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class PortControllerImpl implements PortController {

    private final PortService portService;

    public PortControllerImpl(PortService portService) {
        this.portService = portService;
    }

    @Override
    public PortService getPortService() {
        return this.portService;
    }


    @Override
    @GetMapping(path = "/api/ports/{id}")
    public Port getPort(@PathVariable Long id) {
        return PortController.super.getPort(id);
    }

    @Override
    @DeleteMapping(path = "/api/ports/{id}")
    public None deletePort(@PathVariable Long id) {
        PortController.super.deletePort(id);
        return new None();
    }

    @Override
    @PutMapping("/api/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port updatePort(@PathVariable Long portId, @RequestBody Port port) {
        return PortController.super.updatePort(portId, port);
    }

    @Override
    @PatchMapping("/api/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port changeStatusPort(@PathVariable Long portId) {
        return PortController.super.changeStatusPort(portId);
    }
}
