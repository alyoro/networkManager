package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.DeviceType;
import com.prototype.networkManager.neo4j.domain.PatchPanel;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.MaximumPortNumberReachedException;
import com.prototype.networkManager.neo4j.exceptions.PatchPanelNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PortNumberAlreadyInListException;
import com.prototype.networkManager.neo4j.repository.PatchPanelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;


@Service
public class PatchPanelServiceImpl implements PatchPanelService{

    @Autowired
    PatchPanelRepository patchPanelRepository;

    @Autowired
    HelperFunctions helperFunctions;

    @Override
    public PatchPanel getPatchPanel(Long id) throws PatchPanelNotFoundException {
        Optional<PatchPanel> patchPanel = patchPanelRepository.findById(id);
        if(patchPanel.isPresent()){
            return patchPanel.get();
        }else{
            throw new PatchPanelNotFoundException("PatchPanel with id: "+id+" not found.");
        }
    }

    @Override
    public Iterable<PatchPanel> getPatchPanels(){
        return patchPanelRepository.findAll(2);
    }

    @Override
    public void deletePatchPanel(Long id) throws PatchPanelNotFoundException {
        if(patchPanelRepository.findById(id).isPresent()){
            patchPanelRepository.deleteById(id);
        }else{
            throw new PatchPanelNotFoundException("PatchPanel with id: "+id+" not found.");
        }
    }

    @Override
    public PatchPanel createPatchPanel(PatchPanel patchPanel){
        return patchPanelRepository.save(patchPanel);
    }

    @Override
    public void addPort(Long id, Port port) throws PortNumberAlreadyInListException, PatchPanelNotFoundException, MaximumPortNumberReachedException{

//        Optional<PatchPanel> patchPanel = patchPanelRepository.findById(id);
//        if(patchPanel.isEmpty()) {
//            throw new PatchPanelNotFoundException("PatchPanel with id: "+id+" not found.");
//        }
//        else {
//	        List<Port> ports = patchPanel.get().getPorts();
//	        if(patchPanel.get().getNumberOfPorts() == ports.size()){
//	            throw new MaximumPortNumberReachedException("Cant put more ports in this device");
//            }
//            port.setDevicePlugged(DeviceType.None);
//            port.setPortOnTheUpperElement("None");
//	        if(ports != null){
//                if(helperFunctions.arePortNumberListUnique(ports, port.getPortNumber())){
//                    ports.add(port);
//                } else{
//                    throw new PortNumberAlreadyInListException("Port with this number already added to device");
//                }
//	        } else{
//		        ports = new ArrayList<>();
//		        ports.add(port);
//	        }
//	    patchPanel.get().setPorts(ports);
//	    patchPanelRepository.save(patchPanel.get());
//	    }
    }
}
