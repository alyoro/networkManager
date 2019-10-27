package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.RoomSocket;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.RoomSocketNotFoundException;
import com.prototype.networkManager.neo4j.repository.RoomSocketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RoomSocketServiceImpl implements RoomSocketService {

    private final RoomSocketRepository roomSocketRepository;

    private final PortService portService;

    public RoomSocketServiceImpl(RoomSocketRepository roomSocketRepository, PortService portService) {
        this.roomSocketRepository = roomSocketRepository;
        this.portService = portService;
    }

    @Override
    public RoomSocket getRoomSocket(Long id) throws RoomSocketNotFoundException {
        Optional<RoomSocket> switchOptional = roomSocketRepository.findById(id);
        if (switchOptional.isPresent()) {
            return switchOptional.get();
        } else {
            throw new RoomSocketNotFoundException("RoomSocket with id: " + id + " not found.");
        }
    }

    @Override
    public Iterable<RoomSocket> getRoomSockets() {
        return roomSocketRepository.findAll(2);
    }

    @Override
    public void deleteRoomSocket(Long id) throws RoomSocketNotFoundException, PortNotFoundException {
        Optional<RoomSocket> roomSocketOptional = roomSocketRepository.findById(id);
        if (roomSocketOptional.isPresent()) {
            if (!(roomSocketOptional.get().getPorts() == null)) {
                for (Port port : roomSocketOptional.get().getPorts()) {
                    portService.deletePort(port.getId());
                }
            }
            roomSocketRepository.deleteById(id);
        } else {
            throw new RoomSocketNotFoundException("RoomSocket with id: " + id + " not found.");
        }
    }

    @Override
    public RoomSocket createRoomSocket(RoomSocket roomSocket) {
        roomSocket.setPorts(portService.createMultiplePorts(roomSocket.getNumberOfPorts(), false));
        return roomSocketRepository.save(roomSocket);
    }

    @Override
    public RoomSocket updateRoomSocket(Long id, RoomSocket roomSocket) throws RoomSocketNotFoundException {
        Optional<RoomSocket> roomSocketOptional = roomSocketRepository.findById(id);
        if (roomSocketOptional.isPresent()) {
            roomSocketOptional.get().setBuilding(roomSocket.getBuilding());
            roomSocketOptional.get().setRoom(roomSocket.getRoom());
            roomSocketOptional.get().setIdentifier(roomSocket.getIdentifier());
            roomSocketOptional.get().setDescription(roomSocket.getDescription());

            return roomSocketRepository.save(roomSocketOptional.get());
        } else {
            throw new RoomSocketNotFoundException("RoomSocket with id: " + id + " not found");
        }
    }

    @Override
    public String createRoomSocketReport(Long id) throws RoomSocketNotFoundException {
        RoomSocket roomSocket = this.getRoomSocket(id);

        StringBuilder text = new StringBuilder();

        text.append("Room Socket - {" + roomSocket.getIdentifier() + "} + Properties\n");
        text.append("Id: " + roomSocket.getId() + "\n");
        text.append("Identifier: " + roomSocket.getIdentifier() + "\n");
        text.append("Number of ports: " + roomSocket.getNumberOfPorts() + "\n");
        text.append("Building: " + roomSocket.getBuilding() + "\n");
        text.append("Room: " + roomSocket.getRoom() + "\n");
        text.append("Description: " + roomSocket.getDescription() + "\n");

        return text.toString();
    }

    @Override
    public String createRoomSocketsReport() {
        Iterable<RoomSocket> roomSockets = this.getRoomSockets();

        StringBuilder text = new StringBuilder();

        text.append("Room Socket - All\n");
        text.append("Id" + getSepList() + "Identifier" + getSepList() + "No. Ports" + getSepList() + "Building" + getSepList() + "Room" + getSepList() + "Description" + "\n");

        for (RoomSocket roomSocket : roomSockets) {
            text.append(roomSocket.getId() + getSepList() + roomSocket.getIdentifier() + getSepList() +
                    roomSocket.getNumberOfPorts() + getSepList() + roomSocket.getBuilding() + getSepList() +
                    roomSocket.getRoom() + getSepList() + roomSocket.getDescription() + "\n");
        }

        return text.toString();
    }

    @Override
    public String createRoomSocketsReportCSV() {
        Iterable<RoomSocket> roomSockets = this.getRoomSockets();

        StringBuilder text = new StringBuilder();

        text.append("Room Socket - All\n");
        text.append("Id" + getSepList() + "Identifier" + getSepListCSV() + "No. Ports" + getSepListCSV() + "Building" + getSepListCSV() + "Room" + getSepListCSV() + "Description" + "\n");

        for (RoomSocket roomSocket : roomSockets) {
            text.append(roomSocket.getId() + getSepListCSV() + roomSocket.getIdentifier() + getSepListCSV() +
                    roomSocket.getNumberOfPorts() + getSepListCSV() + roomSocket.getBuilding() + getSepListCSV() +
                    roomSocket.getRoom() + getSepListCSV() + roomSocket.getDescription() + "\n");
        }

        return text.toString();
    }

    private String getSepList() {
        return "\t\t";
    }

    private String getSepListCSV() {
        return ";";
    }
}
