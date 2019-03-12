package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.PatchPanel;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.PatchPanelNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PortNumberAlreadyInListException;
import com.prototype.networkManager.neo4j.services.PatchPanelService;
import com.prototype.networkManager.neo4j.services.PortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api")
public class PatchPanelController implements PortController{

    @Autowired
    PatchPanelService patchPanelService;

    @Autowired
    PortService portService;

    @Override
    public PortService getPortService() {
        return portService;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/patchpanels")
    Iterable<PatchPanel> getPatchPanels(){
        return patchPanelService.getPatchPanels();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/patchpanels/{id}")
    PatchPanel getPatchPanel(@PathVariable("id") Long id){
        try{
            return patchPanelService.getPatchPanel(id);
        } catch(PatchPanelNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/patchpanels")
    @ResponseStatus(HttpStatus.CREATED)
    PatchPanel createPatchPanel(@RequestBody PatchPanel patchPanel){
        return patchPanelService.createPatchPanel(patchPanel);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @DeleteMapping("/patchpanels/{id}")
    void deletePatchPanel(@PathVariable("id") Long id){
        try{
            patchPanelService.deletePatchPanel(id);
        }catch(PatchPanelNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/patchpanels/{id}/ports")
    public Iterable<Port> getPorts(@PathVariable("id") Long id) {
        return portService.getPorts(id);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @Override
    @PostMapping("/patchpanels/{id}/ports")
    public void createPort(@PathVariable("id") Long id, @RequestBody Port port) {
        try{
            patchPanelService.addPort(id, port);
        } catch(PortNumberAlreadyInListException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch(PatchPanelNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
