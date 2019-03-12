package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.Connection;
import com.prototype.networkManager.neo4j.domain.PatchPanel;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.PortNumberAlreadyInListException;
import com.prototype.networkManager.neo4j.services.ConnectionService;
import com.prototype.networkManager.neo4j.services.PatchPanelService;
import com.prototype.networkManager.neo4j.services.PortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiCalls implements PortController{


    private final
    PatchPanelService patchPanelService;

    private final
    ConnectionService connectionService;

    private  final
    PortService portService;

    @Override
    public PortService getPortService() {
        return portService;
    }

    @Autowired
    public ApiCalls(PatchPanelService patchPanelService, ConnectionService connectionService, PortService portService) {
        this.patchPanelService = patchPanelService;
        this.connectionService = connectionService;
        this.portService = portService;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/PATCH_PANEL/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPatchPanel(@RequestBody PatchPanel patchPanel){
        System.out.println("AddPP");
        patchPanelService.addPatchPanel(patchPanel);

    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/PATCH_PANEL/getAll")
    public Iterable<PatchPanel> getAllPatchPanel(){
        return patchPanelService.findAll();
    }


    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/PATCH_PANEL/addPort")
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
    @PostMapping("/connections")
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

    @Override
    @PostMapping(value = "patchpanels/{id}/ports", consumes = {"text/plain", "application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public void createPort(@PathVariable("id") Long id, Port port) {
        try{
            patchPanelService.addPort(id,port);
        }catch(PortNumberAlreadyInListException e){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Port number already in device list", e);
        }

    }
}
