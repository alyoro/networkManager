package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.AccessPoint;
import com.prototype.networkManager.neo4j.domain.None;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.AccessPointNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.services.AccessPointService;
import com.prototype.networkManager.neo4j.services.PortService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AccessPoint Controller
 * Controller which handles requests to AccessPoints Devices and it's ports.
 * API Url: {@code /api/accesspoints/}
 */
@RestController
public class AccessPointController implements PortController {

    private final AccessPointService accessPointService;

    private final PortService portService;

    public AccessPointController(AccessPointService accessPointService, PortService portService) {
        this.accessPointService = accessPointService;
        this.portService = portService;
    }


    /**
     * GET ALL
     *
     * @return Returns all Access Points in application
     */
    @GetMapping("/api/accesspoints")
    @ResponseStatus(HttpStatus.OK)
    Iterable<AccessPoint> getAccessPoints() {
        return accessPointService.getAccessPoints();
    }

    /**
     * GET SPECIFIC DEVICE
     *
     * @param id ID of device to return
     * @return Return Access Point with specific ID
     */
    @GetMapping("/api/accesspoints/{id}")
    @ResponseStatus(HttpStatus.OK)
    AccessPoint getAccesPoint(@PathVariable Long id) {
        try {
            return accessPointService.getAccessPoint(id);
        } catch (AccessPointNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * CREATE DEVICE
     *
     * @param accessPoint Request Body to save in Database
     * @return Newly added Access Point device
     */
    @PostMapping("/api/accesspoints")
    @ResponseStatus(HttpStatus.CREATED)
    AccessPoint createAccessPoint(@RequestBody AccessPoint accessPoint) {
        return accessPointService.createAccessPoint(accessPoint);
    }

    /**
     * DELETE DEVICE
     *
     * @param id ID of device to delete
     * @return Empty entity when successful deletion
     */
    @DeleteMapping("/api/accesspoints/{id}")
    @ResponseStatus(HttpStatus.OK)
    None deleteAccessPoint(@PathVariable Long id) {
        try {
            accessPointService.deleteAccessServer(id);
            return new None();
        } catch (AccessPointNotFoundException | PortNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * UPDATE DEVICE
     *
     * @param id          ID of device to update
     * @param accessPoint Request body of updated device
     * @return Updated Access Point device
     */
    @PutMapping("/api/accesspoints/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    AccessPoint updateAccessPoint(@PathVariable Long id, @RequestBody AccessPoint accessPoint) {
        try {
            return accessPointService.updateAccessPoint(id, accessPoint);
        } catch (AccessPointNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //TODO report
    @GetMapping("/api/accesspoints/{id}/report")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getAccessPointReport(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Content-Disposition", "attachment; filename=report.txt")
                    .body(accessPointService.createAccessPointReport(id));
        } catch (AccessPointNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //TODO report
    @GetMapping("/api/accesspoints/report")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getAccessPointsReport() {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Disposition", "attachment; filename=report.txt")
                .body(accessPointService.createAccessPointsReport());
    }

    //TODO report
    @GetMapping(value = "/api/accesspoints/report/csv", produces = "text/csv")
    @ResponseStatus(HttpStatus.OK)
    public void getAccessPointsReportCSV(HttpServletResponse response) {
        try {
            response.setContentType("text/plain; charset=utf-8");
            response.addHeader("Content-Disposition", "attachment; filename=report.csv");
            response.getWriter().print(accessPointService.createAccessPointsReportCSV());
        } catch (IOException e) {
            System.out.println(e);
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
    @GetMapping("/api/accesspoints/{id}/ports")
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
    @GetMapping("/api/accesspoints/{id}/ports/{portId}")
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
    @DeleteMapping("/api/accesspoints/{id}/ports/{portId}")
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
    @PostMapping("/api/accesspoints/{id}/ports")
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
    @PutMapping("/api/accesspoints/{id}/ports/{portId}")
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
    @PatchMapping("/api/accesspoints/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port changeStatusPort(@PathVariable Long portId) {
        return PortController.super.changeStatusPort(portId);
    }
}
