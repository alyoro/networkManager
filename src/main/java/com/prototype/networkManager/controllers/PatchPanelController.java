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

    @GetMapping("/api/patchpanels")
    @ResponseStatus(HttpStatus.OK)
    Iterable<PatchPanel> getPatchPanels(){
        return patchPanelService.getPatchPanels();
    }

    @GetMapping("/api/patchpanels/{id}")
    @ResponseStatus(HttpStatus.OK)
    PatchPanel getPatchPanel(@PathVariable Long id){
        try{
            return patchPanelService.getPatchPanel(id);
        } catch(PatchPanelNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/api/patchpanels/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deletePatchPanel(@PathVariable Long id){
        try{
            patchPanelService.deletePatchPanel(id);
        }catch(PatchPanelNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/api/patchpanels")
    @ResponseStatus(HttpStatus.CREATED)
    PatchPanel createPatchPanel(@RequestBody PatchPanel patchPanel){
        return patchPanelService.createPatchPanel(patchPanel);
    }

    //-------------------- PortController --------------------

    @Override
    public PortService getPortService() {
        return portService;
    }

    @Override
    @GetMapping("/api/patchpanels/{id}/ports")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Port> getPorts(@PathVariable Long id) {
        return PortController.super.getPorts(id);
    }

    @Override
    @GetMapping("/api/patchpanels/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port getPort(@PathVariable Long portId) {
        return PortController.super.getPort(portId);
    }

    @Override
    @DeleteMapping("/api/patchpanels/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePort(@PathVariable Long portId) {
        PortController.super.deletePort(portId);
    }

    @Override
    @PostMapping("/api/patchpanels/{id}/ports")
    @ResponseStatus(HttpStatus.CREATED)
    public Port createPort(@PathVariable Long id, @RequestBody Port port) {
        return PortController.super.createPort(id, port);
    }
}
