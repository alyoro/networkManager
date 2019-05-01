package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.Connection;
import com.prototype.networkManager.neo4j.domain.None;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.ConnectionCantCreatedException;
import com.prototype.networkManager.neo4j.exceptions.ConnectionNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ConnectionController {


    @Autowired
    ConnectionService connectionService;

    @GetMapping("/connections")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Connection> getConnections() {
        return connectionService.getConnections();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/connections")
    public Connection makeConnection(@RequestBody List<Port> ports) {
        try {
            return connectionService.makeConnection(ports);
        } catch (PortNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (ConnectionCantCreatedException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

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
