package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.None;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.RoomSocket;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.RoomSocketNotFoundException;
import com.prototype.networkManager.neo4j.services.PortService;
import com.prototype.networkManager.neo4j.services.RoomSocketService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * RoomSocket Controller
 * Controller which handles requests to Room Sockets Devices and it's ports.
 * API Url: {@code /api/roomsockets/}
 */
@RestController
public class RoomSocketController implements PortController {

    private final RoomSocketService roomSocketService;

    private final PortService portService;

    public RoomSocketController(RoomSocketService roomSocketService, PortService portService) {
        this.roomSocketService = roomSocketService;
        this.portService = portService;
    }

    /**
     * GET ALL
     *
     * @return Returns all Room Sockets in application
     */
    @GetMapping("/api/roomsockets")
    Iterable<RoomSocket> getRoomSocket() {
        return roomSocketService.getRoomSockets();
    }

    /**
     * GET SPECIFIC DEVICE
     *
     * @param id ID of device to return
     * @return Return Room Socket with specific ID
     */
    @GetMapping("/api/roomsockets/{id}")
    @ResponseStatus(HttpStatus.OK)
    RoomSocket getRoomSocket(@PathVariable Long id) {
        try {
            return roomSocketService.getRoomSocket(id);
        } catch (RoomSocketNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * CREATE DEVICE
     *
     * @param roomSocket Request Body to save in Database
     * @return Newly added Room Socket device
     */
    @PostMapping("/api/roomsockets")
    @ResponseStatus(HttpStatus.CREATED)
    RoomSocket createRoomSocket(@RequestBody RoomSocket roomSocket) {
        return roomSocketService.createRoomSocket(roomSocket);
    }

    /**
     * DELETE DEVICE
     *
     * @param id ID of device to delete
     * @return Empty entity when successful deletion
     */
    @DeleteMapping("/api/roomsockets/{id}")
    @ResponseStatus(HttpStatus.OK)
    None deleteRoomSocket(@PathVariable Long id) {
        try {
            roomSocketService.deleteRoomSocket(id);
            return new None();
        } catch (RoomSocketNotFoundException | PortNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * UPDATE DEVICE
     *
     * @param id         ID of device to update
     * @param roomSocket Request body of updated device
     * @return Updated Room Socket device
     */
    @PutMapping("/api/roomsockets/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    RoomSocket updatePatchPanel(@PathVariable Long id, @RequestBody RoomSocket roomSocket) {
        try {
            return roomSocketService.updateRoomSocket(id, roomSocket);
        } catch (RoomSocketNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    //-------------------- PortController --------------------

    @Override
    public PortService getPortService() {
        return portService;
    }

    /**
     * RETURN DEVICE PORTS
     * Returns all ports that are part of specific device
     *
     * @param id ID of device
     * @return Ports of specific device
     */
    @Override
    @GetMapping("/api/roomsockets/{id}/ports")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Port> getPorts(@PathVariable Long id) {
        return PortController.super.getPorts(id);
    }

    /**
     * GET ONE PORT
     * Return Specific port(portId) of specific Device(id)
     *
     * @param portId ID of port to GET
     * @return Port with given port Id
     */
    @Override
    @GetMapping("/api/roomsockets/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port getPort(@PathVariable Long portId) {
        return PortController.super.getPort(portId);
    }

    /**
     * DELETE PORT
     *
     * @param portId ID of port to delete
     * @return Empty Entity when successful delete
     */
    @Override
    @DeleteMapping("/api/roomsockets/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public None deletePort(@PathVariable Long portId) {
        return PortController.super.deletePort(portId);
    }

    /**
     * CREATE PORT FOR DEVICE
     *
     * @param id   ID of device to add new port
     * @param port Request Body with Port to add
     * @return Newly added Port Entity
     */
    @Override
    @PostMapping("/api/roomsockets/{id}/ports")
    @ResponseStatus(HttpStatus.CREATED)
    public Port createPort(@PathVariable Long id, @RequestBody Port port) {
        return PortController.super.createPort(id, port);
    }

    /**
     * UPDATE PORT
     *
     * @param portId ID of port to be updated
     * @param port   Request Body of port to update
     * @return Updated Port
     */
    @Override
    @PutMapping("/api/roomsockets/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port updatePort(@PathVariable Long portId, @RequestBody Port port) {
        return PortController.super.updatePort(portId, port);
    }

    /**
     * CHANGE PORT STATUS
     *
     * @param portId ID of port to change status
     * @return Updated Port with changed status
     */
    @Override
    @PatchMapping("/api/roomsockets/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port changeStatusPort(@PathVariable Long portId) {
        return PortController.super.changeStatusPort(portId);
    }
}
