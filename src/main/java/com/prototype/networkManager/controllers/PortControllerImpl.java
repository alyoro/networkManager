package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.None;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.services.PortService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Port Controller
 * Used when action needed on port without specifying device
 * API URL: {@code /api/ports/}
 */
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

    /**
     * RETURN PORT
     * Return port with specific port Id
     *
     * @param portId ID of port to return
     * @return Entity of port
     */
    @Override
    @GetMapping(path = "/api/ports/{portId}")
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
    @DeleteMapping(path = "/api/ports/{portId}")
    public None deletePort(@PathVariable Long portId) {
        PortController.super.deletePort(portId);
        return new None();
    }

    /**
     * UPDATE PORT
     *
     * @param portId ID of port to be updated
     * @param port   Request Body of port to update
     * @return Updated Port
     */
    @Override
    @PutMapping("/api/ports/{portId}")
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
    @PatchMapping("/api/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port changeStatusPort(@PathVariable Long portId) {
        return PortController.super.changeStatusPort(portId);
    }
}
