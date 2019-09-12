package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.exceptions.VlanNameAlreadyInDatabaseException;
import com.prototype.networkManager.neo4j.exceptions.VlanNameNotFoundException;
import com.prototype.networkManager.neo4j.services.VlansService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * VLans Controller
 * Serves API for managing vlans of the application
 * API URL: {@code /api/vlans/}
 */
@RestController
public class VlansController {

    private final VlansService vlansService;

    public VlansController(VlansService vlansService) {
        this.vlansService = vlansService;
    }

    /**
     * GET ALL
     *
     * @return Returns all vlans in application
     */
    @GetMapping("/api/vlans")
    List<String> getVlansNames() {
        return vlansService.getVlansNames();
    }

    /**
     * CREATE VLAN NAME
     *
     * @param name Path Variable - name of new vlan
     * @return Updated list of all vlan names
     */
    @PostMapping("/api/vlans/{name}")
    List<String> addVlansNames(@PathVariable String name) {
        try {
            return vlansService.addVlansNames(name);
        } catch (VlanNameAlreadyInDatabaseException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * UPDATE VLAN NAME
     *
     * @param oldName PathVariable - old vlan name
     * @param name    PathVariable - new vlan name
     * @return Updated list of all vlan names
     */
    @PatchMapping("/api/vlans/{oldName}/{name}")
    List<String> updateVlansNames(@PathVariable String oldName, @PathVariable String name) {
        return vlansService.updateVlansNames(oldName, name);
    }

    /**
     * DELETE VLAN NAME
     *
     * @param name PathVariable - vlan name to delete
     * @return Updated list of all vlan names
     */
    @DeleteMapping("/api/vlans/{name}")
    List<String> deleteVlansNames(@PathVariable String name) {
        try {
            return vlansService.deleteNameFromVlans(name);
        } catch (VlanNameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
