package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.PatchPanel;
import com.prototype.networkManager.neo4j.services.PatchPanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class ApiCalls {


    @Autowired
    PatchPanelService patchPanelService;


    @PostMapping("api/add/PatchPanel")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPatchPanel(@RequestBody PatchPanel patchPanel){
        System.out.println("AddPP");
        patchPanelService.addPatchPanel(patchPanel);

    }
}
