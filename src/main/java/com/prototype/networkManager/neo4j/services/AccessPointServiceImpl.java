package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.AccessPoint;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.AccessPointNotFoundException;
import com.prototype.networkManager.neo4j.repository.AccessPointRepository;
import com.prototype.networkManager.neo4j.repository.PortRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AccessPointServiceImpl implements AccessPointService {

    private final AccessPointRepository accessPointRepository;

    private final PortRepository portRepository;

    public AccessPointServiceImpl(AccessPointRepository accessPointRepository, PortRepository portRepository) {
        this.accessPointRepository = accessPointRepository;
        this.portRepository = portRepository;
    }

    @Override
    public AccessPoint getAccessPoint(Long id) throws AccessPointNotFoundException {
        Optional<AccessPoint> accessPointOptional = accessPointRepository.findById(id);
        if(accessPointOptional.isPresent()){
            return accessPointOptional.get();
        } else {
            throw new AccessPointNotFoundException("AccessPoint with id: "+id+" not found.");
        }
    }

    @Override
    public Iterable<AccessPoint> getAccessPoints() {
        return accessPointRepository.findAll(2);
    }

    @Override
    public void deleteAccessServer(Long id) throws AccessPointNotFoundException {
        Optional<AccessPoint> accessPointOptional = accessPointRepository.findById(id);
        if(accessPointOptional.isPresent()){
            if(!accessPointOptional.get().getPorts().isEmpty()){
                for(Port port: accessPointOptional.get().getPorts()){
                    portRepository.delete(port);
                }
            }
            accessPointRepository.deleteById(id);
        } else {
            throw new AccessPointNotFoundException("AccessPoint wit id: "+id+" not found.");
        }
    }

    @Override
    public AccessPoint createAccessPoint(AccessPoint accessPoint) {
        return accessPointRepository.save(accessPoint);
    }
}
