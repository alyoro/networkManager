package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.PatchPanel;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.services.PatchPanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("/")
public class ApiCalls {


    @Autowired
    PatchPanelService patchPanelService;

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
        patchPanelService.addPort(id,port);
        return "OK";
    }

}
