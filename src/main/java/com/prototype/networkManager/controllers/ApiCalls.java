package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.Connection;
import com.prototype.networkManager.neo4j.domain.PatchPanel;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.PortNumberAlreadyInListException;
import com.prototype.networkManager.neo4j.services.ConnectionService;
import com.prototype.networkManager.neo4j.services.PatchPanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.tags.HtmlEscapeTag;

import java.util.ArrayList;
import java.util.List;

@RestController("/")
public class ApiCalls {


    @Autowired
    PatchPanelService patchPanelService;

    @Autowired
    ConnectionService connectionService;

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("api/PATCH_PANEL/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPatchPanel(@RequestBody PatchPanel patchPanel){
        System.out.println("AddPP");
        patchPanelService.addPatchPanel(patchPanel);

    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("api/PATCH_PANEL/getAll")
    public Iterable<PatchPanel> getAllPatchPanel(){
        return patchPanelService.findAll();
    }


    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("api/PATCH_PANEL/addPort")
    public String addPort(@RequestParam Long id,@RequestBody Port port){
        try{
            patchPanelService.addPort(id,port);
            return "OK";
        }catch(PortNumberAlreadyInListException e){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Port number already in device list", e);
        }

    }


    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("api/connections")
    public Connection addConnection(@RequestBody List<Port> ports){
        Connection conn = null;
        try{
            System.out.println(ports);
            return connectionService.addConnection(ports);
        }catch(Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"addconnection");

        }
    }

}
