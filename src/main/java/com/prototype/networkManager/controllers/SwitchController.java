package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.None;
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
    @ResponseStatus(HttpStatus.OK)
    Iterable<Switch> getSwitches(){
        return switchService.getSwitches();
    }

    @GetMapping("/api/switches/{id}")
    @ResponseStatus(HttpStatus.OK)
    Switch getSwitch(@PathVariable Long id){
        try{
            return switchService.getSwitch(id);
        }catch(SwitchNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/api/switches/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteSwitch(@PathVariable Long id){
        try{
            switchService.deleteSwitch(id);
        }catch(SwitchNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @PostMapping("/api/switches")
    @ResponseStatus(HttpStatus.CREATED)
    Switch createSwitch(@RequestBody Switch switchDevice){
        return switchService.createSwitch(switchDevice);
    }

    //-------------------- PortController --------------------

    @Override
    public PortService getPortService() {
        return portService;
    }

    @Override
    @GetMapping("/api/switches/{id}/ports")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Port> getPorts(@PathVariable Long id) {
        return PortController.super.getPorts(id);
    }

    @Override
    @GetMapping("/api/switches/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port getPort(@PathVariable Long portId) {
        return PortController.super.getPort(portId);
    }

    @Override
    @DeleteMapping("/api/switches/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public None deletePort(@PathVariable Long portId) {
        return PortController.super.deletePort(portId);
    }

    @Override
    @PostMapping("/api/switches/{id}/ports")
    @ResponseStatus(HttpStatus.CREATED)
    public Port createPort(@PathVariable Long id, @RequestBody Port port) {
        return PortController.super.createPort(id, port);
    }

    @Override
    @PutMapping("/api/switches/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port updatePort(Long portId, @RequestBody Port port) {
        return PortController.super.updatePort(portId, port);
    }

    @Override
    @PatchMapping("/api/switches/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port changeStatusPort(Long portId) {
        return PortController.super.changeStatusPort(portId);
    }
}
