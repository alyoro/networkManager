package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.RoomSocket;
import com.prototype.networkManager.neo4j.exceptions.RoomSocketNotFoundException;
import com.prototype.networkManager.neo4j.services.PortService;
import com.prototype.networkManager.neo4j.services.RoomSocketService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class RoomSocketController implements PortController {

    private final RoomSocketService roomSocketService;

    private final PortService portService;

    public RoomSocketController(RoomSocketService roomSocketService, PortService portService) {
        this.roomSocketService = roomSocketService;
        this.portService = portService;
    }

    @GetMapping("/api/roomsockets")
    Iterable<RoomSocket> getRoomSocket(){
        return roomSocketService.getRoomSockets();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/api/roomsockets/{id}")
    RoomSocket getRoomSocket(@PathVariable("id") Long id){
        try{
            return roomSocketService.getRoomSocket(id);
        }catch(RoomSocketNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/api/roomsockets")
    @ResponseStatus(HttpStatus.CREATED)
    RoomSocket createRoomSocket(@RequestBody RoomSocket switchDevice){
        return roomSocketService.createRoomSocket(switchDevice);
    }

    @DeleteMapping("/api/roomsockets/{id}")
    void deleteRoomSocket(@PathVariable("id") Long id){
        try{
            roomSocketService.deleteRoomSocket(id);
        }catch(RoomSocketNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    //-------------------- PortController --------------------

    @Override
    public PortService getPortService() {
        return portService;
    }


    @Override
    @GetMapping("/api/roomsockets/{id}/ports")
    public Iterable<Port> getPorts(@PathVariable("id") Long id) {
        return PortController.super.getPorts(id);
    }

    @Override
    @GetMapping("/api/roomsockets/{id}/ports/{portId}")
    public Port getPort(@PathVariable("portId") Long portId) {
        return PortController.super.getPort(portId);
    }

    @Override
    @DeleteMapping("/api/roomsockets/{id}/ports/{portId}")
    public void deletePort(@PathVariable("portId") Long portId) {
        PortController.super.deletePort(portId);
    }

    @Override
    @PostMapping("/api/roomsockets/{id}/ports")
    @ResponseStatus(HttpStatus.CREATED)
    public Port createPort(@PathVariable("id") Long id, @RequestBody Port port) {
        return PortController.super.createPort(id, port);
    }
}
