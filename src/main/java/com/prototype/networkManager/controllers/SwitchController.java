package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.None;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.Switch;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.SwitchNotFoundException;
import com.prototype.networkManager.neo4j.services.PortService;
import com.prototype.networkManager.neo4j.services.SwitchService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Switch Controller
 * Controller which handles requests to Switches Devices and it's ports.
 * API Url: {@code /api/switches/}
 */
@RestController
public class SwitchController implements PortController {

    private final SwitchService switchService;

    private final PortService portService;

    public SwitchController(SwitchService switchService, PortService portService) {
        this.switchService = switchService;
        this.portService = portService;
    }

    /**
     * GET ALL
     *
     * @return Returns all Switches in application
     */
    @GetMapping("/api/switches")
    @ResponseStatus(HttpStatus.OK)
    Iterable<Switch> getSwitches() {
        return switchService.getSwitches();
    }

    /**
     * GET SPECIFIC DEVICE
     *
     * @param id ID of device to return
     * @return Return Switch with specific ID
     */
    @GetMapping("/api/switches/{id}")
    @ResponseStatus(HttpStatus.OK)
    Switch getSwitch(@PathVariable Long id) {
        try {
            return switchService.getSwitch(id);
        } catch (SwitchNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * CREATE DEVICE
     *
     * @param switchDevice Request Body to save in Database
     * @return Newly added Switch device
     */
    @PostMapping("/api/switches")
    @ResponseStatus(HttpStatus.CREATED)
    Switch createSwitch(@RequestBody Switch switchDevice) {
        return switchService.createSwitch(switchDevice);
    }

    /**
     * DELETE DEVICE
     *
     * @param id ID of device to delete
     * @return Empty entity when successful deletion
     */
    @DeleteMapping("/api/switches/{id}")
    @ResponseStatus(HttpStatus.OK)
    None deleteSwitch(@PathVariable Long id) {
        try {
            switchService.deleteSwitch(id);
            return new None();
        } catch (SwitchNotFoundException | PortNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * UPDATE DEVICE
     *
     * @param id           ID of device to update
     * @param switchDevice Request body of updated device
     * @return Updated Switch device
     */
    @PutMapping("/api/switches/{id}")
    @ResponseStatus(HttpStatus.OK)
    Switch updateSwitch(@PathVariable Long id, @RequestBody Switch switchDevice) {
        try {
            return switchService.updateSwitch(id, switchDevice);
        } catch (SwitchNotFoundException e) {
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
    @GetMapping("/api/switches/{id}/ports")
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
    @GetMapping("/api/switches/{id}/ports/{portId}")
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
    @DeleteMapping("/api/switches/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public None deletePort(@PathVariable Long portId) {
        return PortController.super.deletePort(portId);
    }

    /**
     * CREATE PORT FOR DEVICE
     *
     * @param id   ID of device to add new port
     * @param port Request Body with Port to add
     * @return Newly added Port Entity
     */
    @Override
    @PostMapping("/api/switches/{id}/ports")
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
    @PutMapping("/api/switches/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port updatePort(Long portId, @RequestBody Port port) {
        return PortController.super.updatePort(portId, port);
    }

    /**
     * CHANGE PORT STATUS
     *
     * @param portId ID of port to change status
     * @return Updated Port with changed status
     */
    @Override
    @PatchMapping("/api/switches/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port changeStatusPort(Long portId) {
        return PortController.super.changeStatusPort(portId);
    }
}
