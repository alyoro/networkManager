package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.services.InfoService;
import com.prototype.networkManager.neo4j.services.InfoServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UtilitiesController {

    private final InfoService infoService;

    public UtilitiesController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/api/countingdevices")
    List<InfoServiceImpl.DeviceCount> getCountingDevices(){
        return infoService.countingDevices();
    }
}
