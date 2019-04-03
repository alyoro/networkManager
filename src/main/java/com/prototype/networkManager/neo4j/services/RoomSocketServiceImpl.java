package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.RoomSocket;
import com.prototype.networkManager.neo4j.exceptions.RoomSocketNotFoundException;
import com.prototype.networkManager.neo4j.repository.PortRepository;
import com.prototype.networkManager.neo4j.repository.RoomSocketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RoomSocketServiceImpl implements RoomSocketService {

    private final RoomSocketRepository roomSocketRepository;

    private final PortRepository portRepository;

    public RoomSocketServiceImpl(RoomSocketRepository roomSocketRepository, PortRepository portRepository) {
        this.roomSocketRepository = roomSocketRepository;
        this.portRepository = portRepository;
    }

    @Override
    public RoomSocket getRoomSocket(Long id) throws RoomSocketNotFoundException {
        Optional<RoomSocket> switchOptional = roomSocketRepository.findById(id);
        if(switchOptional.isPresent()){
            return switchOptional.get();
        }else{
            throw new RoomSocketNotFoundException("RoomSocket with id: "+id+" not found.");
        }
    }

    @Override
    public Iterable<RoomSocket> getRoomSockets() {
        return roomSocketRepository.findAll(2);
    }

    @Override
    public void deleteRoomSocket(Long id) throws RoomSocketNotFoundException {
        Optional<RoomSocket> roomSocketOptional = roomSocketRepository.findById(id);
        if(roomSocketOptional.isPresent()){
            if(!roomSocketOptional.get().getPorts().isEmpty()){
                for(Port port: roomSocketOptional.get().getPorts()){
                    portRepository.delete(port);
                }
            }
            roomSocketRepository.deleteById(id);
        } else {
            throw new RoomSocketNotFoundException("RoomSocket with id: "+id+" not found.");
        }
    }

    @Override
    public RoomSocket createRoomSocket(RoomSocket roomSocket) {
        return roomSocketRepository.save(roomSocket);
    }
}
