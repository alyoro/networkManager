package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.None;
import com.prototype.networkManager.neo4j.domain.PatchPanel;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.PatchPanelNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.services.PatchPanelService;
import com.prototype.networkManager.neo4j.services.PortService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * PatchPanel Controller
 * Controller which handles requests to PatchPanels Devices and it's ports.
 * API Url: {@code /api/patchpanels/}
 */
@RestController
public class PatchPanelController implements PortController {

    private final PatchPanelService patchPanelService;

    private final PortService portService;

    public PatchPanelController(PatchPanelService patchPanelService, PortService portService) {
        this.patchPanelService = patchPanelService;
        this.portService = portService;
    }

    /**
     * GET ALL
     *
     * @return Returns all Patch Panels in application
     */
    @GetMapping("/api/patchpanels")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<PatchPanel> getPatchPanels() {
        return patchPanelService.getPatchPanels();
    }

    /**
     * GET SPECIFIC DEVICE
     *
     * @param id ID of device to return
     * @return Return Patch Panel with specific ID
     */
    @GetMapping("/api/patchpanels/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PatchPanel getPatchPanel(@PathVariable Long id) {
        try {
            return patchPanelService.getPatchPanel(id);
        } catch (PatchPanelNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * CREATE DEVICE
     *
     * @param patchPanel Request Body to save in Database
     * @return Newly added Patch Panel device
     */
    @PostMapping("/api/patchpanels")
    @ResponseStatus(HttpStatus.CREATED)
    public PatchPanel createPatchPanel(@RequestBody PatchPanel patchPanel) {
        return patchPanelService.createPatchPanel(patchPanel);
    }

    /**
     * DELETE DEVICE
     *
     * @param id ID of device to delete
     * @return Empty entity when successful deletion
     */
    @DeleteMapping("/api/patchpanels/{id}")
    public None deletePatchPanel(@PathVariable Long id) {
        try {
            patchPanelService.deletePatchPanel(id);
            return new None();
        } catch (PatchPanelNotFoundException | PortNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * UPDATE DEVICE
     *
     * @param id         ID of device to update
     * @param patchPanel Request body of updated device
     * @return Updated Patch Panel device
     */
    @PutMapping("/api/patchpanels/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    PatchPanel updatePatchPanel(@PathVariable Long id, @RequestBody PatchPanel patchPanel) {
        try {
            return patchPanelService.updatePatchPanel(id, patchPanel);
        } catch (PatchPanelNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    //TODO report
    @GetMapping("/api/patchpanels/{id}/report")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getPatchPanelReport(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Content-Disposition", "attachment; filename=report.txt")
                    .body(patchPanelService.createPatchPanelReport(id));
        } catch (PatchPanelNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //-------------------- PortController --------------------

    @Override
    public PortService getPortService() {
        return portService;
    }

    /**
     * RETURN DEVICE PORTS
     * Returns all ports that are part of specific device
     *
     * @param id ID of device
     * @return Ports of specific device
     */
    @Override
    @GetMapping("/api/patchpanels/{id}/ports")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Port> getPorts(@PathVariable Long id) {
        return PortController.super.getPorts(id);
    }

    /**
     * GET ONE PORT
     * Return Specific port(portId) of specific Device(id)
     *
     * @param portId ID of port to GET
     * @return Port with given port Id
     */
    @Override
    @GetMapping("/api/patchpanels/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port getPort(@PathVariable Long portId) {
        return PortController.super.getPort(portId);
    }

    /**
     * DELETE PORT
     *
     * @param portId ID of port to delete
     * @return Empty Entity when successful delete
     */
    @Override
    @DeleteMapping("/api/patchpanels/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public None deletePort(@PathVariable Long portId) {
        PortController.super.deletePort(portId);
        return new None();
    }

    /**
     * CREATE PORT FOR DEVICE
     *
     * @param id   ID of device to add new port
     * @param port Request Body with Port to add
     * @return Newly added Port Entity
     */
    @Override
    @PostMapping("/api/patchpanels/{id}/ports")
    @ResponseStatus(HttpStatus.CREATED)
    public Port createPort(@PathVariable Long id, @RequestBody Port port) {
        return PortController.super.createPort(id, port);
    }

    /**
     * UPDATE PORT
     *
     * @param portId ID of port to be updated
     * @param port   Request Body of port to update
     * @return Updated Port
     */
    @Override
    @PutMapping("/api/patchpanels/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port updatePort(@PathVariable Long portId, @RequestBody Port port) {
        return PortController.super.updatePort(portId, port);
    }

    /**
     * CHANGE PORT STATUS
     *
     * @param portId ID of port to change status
     * @return Updated Port with changed status
     */
    @Override
    @PatchMapping("/api/patchpanels/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port changeStatusPort(@PathVariable Long portId) {
        return PortController.super.changeStatusPort(portId);
    }
}
