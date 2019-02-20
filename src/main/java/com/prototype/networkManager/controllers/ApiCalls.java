package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.PatchPanel;
import com.prototype.networkManager.neo4j.services.PatchPanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("/")
public class ApiCalls {


    @Autowired
    PatchPanelService patchPanelService;


    @PostMapping("api/PatchPanel/add")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "http://localhost:8080")
    public void addPatchPanel(@RequestBody PatchPanel patchPanel){
        System.out.println("AddPP");
        patchPanelService.addPatchPanel(patchPanel);

    }

    @GetMapping("api/PatchPanel/getAll")
    @CrossOrigin(origins = "http://localhost:8080")
    public Iterable<PatchPanel> getAllPatchPanel(){
        return patchPanelService.findAll();
    }
}
