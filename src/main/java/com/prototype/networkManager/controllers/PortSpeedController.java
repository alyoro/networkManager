package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.exceptions.PortSpeedNameAlreadyInDatabaseException;
import com.prototype.networkManager.neo4j.exceptions.PortSpeedNameNotFoundException;
import com.prototype.networkManager.neo4j.services.PortSpeedService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * PortSpeed Controller
 * API URL: {@code /api/portspeednames}
 */
@RestController
public class PortSpeedController {

    private final PortSpeedService portSpeedService;

    public PortSpeedController(PortSpeedService portSpeedService) {
        this.portSpeedService = portSpeedService;
    }

    /**
     * GET ALL
     *
     * @return Returns all PortSpeed names in application
     */
    @GetMapping("/api/portspeednames")
    List<String> getPortSpeedNames() {
        return portSpeedService.getPortSpeedNames();
    }

    /**
     * CREATE PORTSPEED NAME
     *
     * @param name Path Variable - name of new PortSpeed
     * @return Updated list of all PortSpeed names
     */
    @PostMapping("/api/portspeednames/{name}")
    List<String> addPortSpeedNames(@PathVariable String name) {
        try {
            return portSpeedService.addPortSpeedNames(name);
        } catch (PortSpeedNameAlreadyInDatabaseException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * UPDATE PORTSPEED NAME
     *
     * @param oldName PathVariable - old PortSpeed name
     * @param name    PathVariable - new PortSpeed name
     * @return Updated list of all PortSpeed names
     */
    @PatchMapping("/api/portspeednames/{oldName}/{name}")
    List<String> updatePortSpeedNames(@PathVariable String oldName, @PathVariable String name) {
        return portSpeedService.updatePortSpeedNames(oldName, name);
    }

    /**
     * DELETE PORTSPEED NAME
     *
     * @param name PathVariable - PortSpeed name to delete
     * @return Updated list of all PortSpeed names
     */
    @DeleteMapping("/api/portspeednames/{name}")
    List<String> deletePortSpeedNames(@PathVariable String name) {
        try {
            return portSpeedService.deleteNameFromPortSpeed(name);
        } catch (PortSpeedNameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
