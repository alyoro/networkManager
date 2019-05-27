package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.None;
import com.prototype.networkManager.neo4j.domain.PatchPanel;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.PatchPanelNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.services.PatchPanelService;
import com.prototype.networkManager.neo4j.services.PortService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class PatchPanelController implements PortController {

    private final PatchPanelService patchPanelService;

    private final PortService portService;

    public PatchPanelController(PatchPanelService patchPanelService, PortService portService) {
        this.patchPanelService = patchPanelService;
        this.portService = portService;
    }

    @GetMapping("/api/patchpanels")
    @ResponseStatus(HttpStatus.OK)
    Iterable<PatchPanel> getPatchPanels() {
        return patchPanelService.getPatchPanels();
    }

    @GetMapping("/api/patchpanels/{id}")
    @ResponseStatus(HttpStatus.OK)
    PatchPanel getPatchPanel(@PathVariable Long id) {
        try {
            return patchPanelService.getPatchPanel(id);
        } catch (PatchPanelNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/api/patchpanels/{id}")
    None deletePatchPanel(@PathVariable Long id) {
        try {
            patchPanelService.deletePatchPanel(id);
            return new None();
        } catch (PatchPanelNotFoundException | PortNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/api/patchpanels")
    @ResponseStatus(HttpStatus.CREATED)
    PatchPanel createPatchPanel(@RequestBody PatchPanel patchPanel) {
        return patchPanelService.createPatchPanel(patchPanel);
    }

    @PutMapping("/api/patchpanels/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    PatchPanel updatePatchPanel(@PathVariable Long id, @RequestBody PatchPanel patchPanel) {
        try {
            return patchPanelService.updatePatchPanel(id, patchPanel);
        } catch (PatchPanelNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

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
    public None deletePort(@PathVariable Long portId) {
        PortController.super.deletePort(portId);
        return new None();
    }

    @Override
    @PostMapping("/api/patchpanels/{id}/ports")
    @ResponseStatus(HttpStatus.CREATED)
    public Port createPort(@PathVariable Long id, @RequestBody Port port) {
        return PortController.super.createPort(id, port);
    }

    @Override
    @PutMapping("/api/patchpanels/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port updatePort(@PathVariable Long portId, @RequestBody Port port) {
        return PortController.super.updatePort(portId, port);
    }

    @Override
    @PatchMapping("/api/patchpanels/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port changeStatusPort(@PathVariable Long portId) {
        return PortController.super.changeStatusPort(portId);
    }
}
