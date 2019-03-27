package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.PatchPanel;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.PatchPanelNotFoundException;
import com.prototype.networkManager.neo4j.services.PatchPanelService;
import com.prototype.networkManager.neo4j.services.PortService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class PatchPanelController implements PortController{

    private final PatchPanelService patchPanelService;

    private final PortService portService;

    public PatchPanelController(PatchPanelService patchPanelService, PortService portService){
        this.patchPanelService = patchPanelService;
        this.portService = portService;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/api/patchpanels")
    Iterable<PatchPanel> getPatchPanels(){
        return patchPanelService.getPatchPanels();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/api/patchpanels/{id}")
    PatchPanel getPatchPanel(@PathVariable("id") Long id){
        try{
            return patchPanelService.getPatchPanel(id);
        } catch(PatchPanelNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/api/patchpanels")
    @ResponseStatus(HttpStatus.CREATED)
    PatchPanel createPatchPanel(@RequestBody PatchPanel patchPanel){
        return patchPanelService.createPatchPanel(patchPanel);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @DeleteMapping("/api/patchpanels/{id}")
    void deletePatchPanel(@PathVariable("id") Long id){
        try{
            patchPanelService.deletePatchPanel(id);
        }catch(PatchPanelNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //-------------------- PortController --------------------

    @Override
    public PortService getPortService() {
        return portService;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @Override
    @GetMapping("/api/patchpanels/{id}/ports")
    public Iterable<Port> getPorts(@PathVariable("id") Long id) {
        return PortController.super.getPorts(id);
    }

    @Override
    @GetMapping("/api/patchpanels/{id}/ports/{portId}")
    public Port getPort(@PathVariable("portId") Long portId) {
        return PortController.super.getPort(portId);
    }

    @Override
    @DeleteMapping("/api/patchpanels/{id}/ports/{portId}")
    public void deletePort(@PathVariable("portId") Long portId) {
        PortController.super.deletePort(portId);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @Override
    @PostMapping("/api/patchpanels/{id}/ports")
    public void createPort(@PathVariable("id") Long id, @RequestBody Port port) {
        PortController.super.createPort(id, port);
    }
}
