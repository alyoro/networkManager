package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.Connection;
import com.prototype.networkManager.neo4j.domain.Port;
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
    public Iterable<Connection> getConnections(){
        return connectionService.getConnections();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/connections")
    public Connection addConnection(@RequestBody List<Port> ports){
        try{
            return connectionService.addConnection(ports);
        }catch(Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"addconnection");
        }
    }

}
