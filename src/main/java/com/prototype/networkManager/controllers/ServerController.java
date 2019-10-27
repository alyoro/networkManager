package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.None;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.Server;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.ServerNotFoundException;
import com.prototype.networkManager.neo4j.services.PortService;
import com.prototype.networkManager.neo4j.services.ServerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Server Controller
 * Controller which handles requests to Servers Devices and it's ports.
 * API Url: {@code /api/servers/}
 */
@RestController
public class ServerController implements PortController {

    private final ServerService serverService;

    private final PortService portService;

    public ServerController(ServerService serverService, PortService portService) {
        this.serverService = serverService;
        this.portService = portService;
    }

    /**
     * GET ALL
     *
     * @return Returns all Servers in application
     */
    @GetMapping("/api/servers")
    @ResponseStatus(HttpStatus.OK)
    Iterable<Server> getServers() {
        return serverService.getServers();
    }

    /**
     * GET SPECIFIC DEVICE
     *
     * @param id ID of device to return
     * @return Return Server with specific ID
     */
    @GetMapping("/api/servers/{id}")
    @ResponseStatus(HttpStatus.OK)
    Server getServer(@PathVariable Long id) {
        try {
            return serverService.getServer(id);
        } catch (ServerNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * CREATE DEVICE
     *
     * @param server Request Body to save in Database
     * @return Newly added Server device
     */
    @PostMapping("/api/servers")
    @ResponseStatus(HttpStatus.CREATED)
    Server createServer(@RequestBody Server server) {
        return serverService.createServer(server);
    }

    /**
     * DELETE DEVICE
     *
     * @param id ID of device to delete
     * @return Empty entity when successful deletion
     */
    @DeleteMapping("/api/servers/{id}")
    @ResponseStatus(HttpStatus.OK)
    None deleteServer(@PathVariable Long id) {
        try {
            serverService.deleteServer(id);
            return new None();
        } catch (ServerNotFoundException | PortNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * UPDATE DEVICE
     *
     * @param id     ID of device to update
     * @param server Request body of updated device
     * @return Updated Server device
     */
    @PutMapping("/api/servers/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    Server updateServer(@PathVariable Long id, @RequestBody Server server) {
        try {
            return serverService.updateServer(id, server);
        } catch (ServerNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //TODO report
    @GetMapping("/api/servers/{id}/report")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getServerReport(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Content-Disposition", "attachment; filename=report.txt")
                    .body(serverService.createServerReport(id));
        } catch (ServerNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //TODO report
    @GetMapping("/api/servers/report")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getServeresReport() {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Disposition", "attachment; filename=report.txt")
                .body(serverService.createServersReport());
    }

    //TODO report
    @GetMapping(value = "/api/servers/report/csv", produces = "text/csv")
    @ResponseStatus(HttpStatus.OK)
    public void getServeresReportCSV(HttpServletResponse response) {
        try {
            response.setContentType("text/plain; charset=utf-8");
            response.addHeader("Content-Disposition", "attachment; filename=report.csv");
            response.getWriter().print(serverService.createServersReportCSV());
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
    @GetMapping("/api/servers/{id}/ports")
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
    @GetMapping("/api/servers/{id}/ports/{portId}")
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
    @DeleteMapping("/api/servers/{id}/ports/{portId}")
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
    @PostMapping("/api/servers/{id}/ports")
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
    @PutMapping("/api/servers/{id}/ports/{portId}")
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
    @PatchMapping("/api/servers/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port changeStatusPort(@PathVariable Long portId) {
        return PortController.super.changeStatusPort(portId);
    }
}
