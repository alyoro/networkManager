package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.Switch;
import com.prototype.networkManager.neo4j.exceptions.SwitchNotFoundException;
import com.prototype.networkManager.neo4j.services.PortService;
import com.prototype.networkManager.neo4j.services.SwitchService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class SwitchController implements PortController{

    private final SwitchService switchService;

    private final PortService portService;

    public SwitchController(SwitchService switchService, PortService portService){
        this.switchService = switchService;
        this.portService = portService;
    }

    @GetMapping("/api/switches")
    Iterable<Switch> getSwitches(){
        return switchService.getSwitches();
    }


    @GetMapping("/api/switches/{id}")
    Switch getSwitch(@PathVariable("id") Long id){
        try{
            return switchService.getSwitch(id);
        }catch(SwitchNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/api/switches")
    @ResponseStatus(HttpStatus.CREATED)
    Switch createSwitch(@RequestBody Switch switchDevice){
        return switchService.createSwitch(switchDevice);
    }


    @DeleteMapping("/api/switches/{id}")
    void deleteSwitch(@PathVariable("id") Long id){
        try{
            switchService.deleteSwitch(id);
        }catch(SwitchNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    //-------------------- PortController --------------------

    @Override
    public PortService getPortService() {
        return portService;
    }


    @Override
    @GetMapping("/api/switches/{id}/ports")
    public Iterable<Port> getPorts(@PathVariable("id") Long id) {
        return PortController.super.getPorts(id);
    }

    @Override
    @GetMapping("/api/switches/{id}/ports/{portId}")
    public Port getPort(@PathVariable("portId") Long portId) {
        return PortController.super.getPort(portId);
    }

    @Override
    @DeleteMapping("/api/switches/{id}/ports/{portId}")
    public void deletePort(@PathVariable("portId") Long portId) {
        PortController.super.deletePort(portId);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @Override
    @PostMapping("/api/switches/{id}/ports")
    @ResponseStatus(HttpStatus.CREATED)
    public Port createPort(@PathVariable("id") Long id, @RequestBody Port port) {
        return PortController.super.createPort(id, port);
    }
}
