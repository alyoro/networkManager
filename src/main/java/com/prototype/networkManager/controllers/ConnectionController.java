package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.Connection;
import com.prototype.networkManager.neo4j.domain.None;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.enums.ConnectionType;
import com.prototype.networkManager.neo4j.exceptions.ConnectionCantCreatedException;
import com.prototype.networkManager.neo4j.exceptions.ConnectionNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Connection Controller
 * API URL: {@code /api/connections/}
 */
@RestController
@RequestMapping("/api")
public class ConnectionController {


    @Autowired
    ConnectionService connectionService;

    /**
     * GET CONNECTIONS
     * Return all connections in database
     *
     * @return List of all connections in the application
     */
    @GetMapping("/connections")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Connection> getConnections() {
        return connectionService.getConnections();
    }

    /**
     * CREATE CONNECTION
     * Creates connections between two ports
     *
     * @param ports          List of two ports(master-slave) first one in master which means the slave is connecting to this ports. This implicate the direction of the connection in database.
     * @param connectionType Type of Connection (SOCKET, PLUG) Default should be SOCKET, PLUG is used to indicate inner end of PatchPanel
     * @return Newly added connection to the system
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/connections/{connectionType}")
    public Connection makeConnection(@RequestBody List<Port> ports, @PathVariable ConnectionType connectionType) {
        try {
            return connectionService.makeConnection(ports, connectionType);
        } catch (PortNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (ConnectionCantCreatedException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * DELETE CONNECTION
     * Deletes connection by its id
     *
     * @param connectionId ID of Connection to delete
     * @return Empty Entity when successful delete
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/connections/{connectionId}")
    public None deleteConnection(@PathVariable Long connectionId) {
        try {
            connectionService.deleteConnection(connectionId);
            return new None();
        } catch (ConnectionNotFoundException | PortNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
