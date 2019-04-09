package com.prototype.networkManager.controllers;


import com.fasterxml.jackson.databind.JsonSerializer;
import com.prototype.networkManager.neo4j.domain.None;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.services.PortService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
