package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.None;
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
    Iterable<RoomSocket> getRoomSocket() {
        return roomSocketService.getRoomSockets();
    }

    @GetMapping("/api/roomsockets/{id}")
    @ResponseStatus(HttpStatus.OK)
    RoomSocket getRoomSocket(@PathVariable Long id) {
        try {
            return roomSocketService.getRoomSocket(id);
        } catch (RoomSocketNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/api/roomsockets/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteRoomSocket(@PathVariable Long id) {
        try {
            roomSocketService.deleteRoomSocket(id);
        } catch (RoomSocketNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/api/roomsockets")
    @ResponseStatus(HttpStatus.CREATED)
    RoomSocket createRoomSocket(@RequestBody RoomSocket switchDevice) {
        return roomSocketService.createRoomSocket(switchDevice);
    }

    //-------------------- PortController --------------------

    @Override
    public PortService getPortService() {
        return portService;
    }


    @Override
    @GetMapping("/api/roomsockets/{id}/ports")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Port> getPorts(@PathVariable Long id) {
        return PortController.super.getPorts(id);
    }

    @Override
    @GetMapping("/api/roomsockets/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port getPort(@PathVariable Long portId) {
        return PortController.super.getPort(portId);
    }

    @Override
    @DeleteMapping("/api/roomsockets/{id}/ports/{portId}")

    @ResponseStatus(HttpStatus.OK)
    public None deletePort(@PathVariable Long portId) {
        return PortController.super.deletePort(portId);
    }

    @Override
    @PostMapping("/api/roomsockets/{id}/ports")
    @ResponseStatus(HttpStatus.CREATED)
    public Port createPort(@PathVariable Long id, @RequestBody Port port) {
        return PortController.super.createPort(id, port);
    }

    @Override
    @PutMapping("/api/roomsockets/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port updatePort(@PathVariable Long portId, @RequestBody Port port) {
        return PortController.super.updatePort(portId, port);
    }

    @Override
    @PatchMapping("/api/roomsockets/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port changeStatusPort(@PathVariable Long portId) {
        return PortController.super.changeStatusPort(portId);
    }
}
