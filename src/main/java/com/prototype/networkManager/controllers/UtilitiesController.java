package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.DeviceNode;
import com.prototype.networkManager.neo4j.exceptions.DeviceNotFoundException;
import com.prototype.networkManager.neo4j.services.InfoService;
import com.prototype.networkManager.neo4j.services.InfoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Utilities Controller
 * Serves utility features
 */
@RestController
public class UtilitiesController {

    private final InfoService infoService;

    public UtilitiesController(InfoService infoService) {
        this.infoService = infoService;
    }

    /**
     * GET COUNTDEVICES
     * API URL: {@code /api/countingdevices}
     *
     * @return List of counts of each device type
     */
    @GetMapping("/api/countingdevices")
    @ResponseStatus(HttpStatus.OK)
    List<InfoServiceImpl.DeviceCount> getCountingDevices() {
        return infoService.countingDevices();
    }

    /**
     * Returns device connected to given port
     * GET CONNECTED DEVICE
     * API URL: {@code /api/connecteddevice/{portId}}
     *
     * @param portId Id of port to search device
     * @return Entity of device connected to the given port
     */
    @GetMapping("/api/connecteddevice/{portId}")
    @ResponseStatus(HttpStatus.OK)
    DeviceNode getConnectedDevices(@PathVariable Long portId) {
        try {
            return infoService.connectedDeviceByPortId(portId);
        } catch (DeviceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
