package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.RoomSocket;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.RoomSocketNotFoundException;

public interface RoomSocketService {
    RoomSocket getRoomSocket(Long id) throws RoomSocketNotFoundException;

    Iterable<RoomSocket> getRoomSockets();

    void deleteRoomSocket(Long id) throws RoomSocketNotFoundException, PortNotFoundException;

    RoomSocket createRoomSocket(RoomSocket roomSocket);

    RoomSocket updateRoomSocket(Long id, RoomSocket roomSocket) throws RoomSocketNotFoundException;
}
