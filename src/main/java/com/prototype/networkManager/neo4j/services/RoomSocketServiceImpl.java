package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.RoomSocket;
import com.prototype.networkManager.neo4j.exceptions.RoomSocketNotFoundException;
import com.prototype.networkManager.neo4j.repository.RoomSocketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RoomSocketServiceImpl implements RoomSocketService {

    private final RoomSocketRepository roomSocketRepository;

    public RoomSocketServiceImpl(RoomSocketRepository roomSocketRepository){
        this.roomSocketRepository = roomSocketRepository;
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
        if(roomSocketRepository.findById(id).isPresent()){
            roomSocketRepository.deleteById(id);
        }else{
            throw new RoomSocketNotFoundException("RoomSocket with id: "+id+" not found.");
        }
    }

    @Override
    public RoomSocket createRoomSocket(RoomSocket roomSocket) {
        return roomSocketRepository.save(roomSocket);
    }
}
