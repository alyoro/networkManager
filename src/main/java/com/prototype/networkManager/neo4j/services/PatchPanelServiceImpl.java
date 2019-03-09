package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.PatchPanel;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.PortNumberAlreadyInListException;
import com.prototype.networkManager.neo4j.repository.PatchPanelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.annotation.Depth;
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

    public void addPatchPanel(PatchPanel patchPanel){
        patchPanelRepository.save(patchPanel);
    }


    public Iterable<PatchPanel> findAll(){
        return patchPanelRepository.findAll(2);
    }



    @Override
    public void addPort(Long id, Port port) throws PortNumberAlreadyInListException{
//        System.out.println(port.getDevicePlugged());

        Optional<PatchPanel> patchPanel = patchPanelRepository.findById(id);
        if(patchPanel == null) {
            return; // TODO throw
        }
        else {
	        List<Port> ports = patchPanel.get().getPorts();
	        if(ports != null){
                if(helperFunctions.arePortNumberListUnique(ports, port.getPortNumber())){
                    ports.add(port);
                } else{
                    throw new PortNumberAlreadyInListException("Port with this number already added to device");
                }
	        } else{
		        ports = new ArrayList<>();
		        ports.add(port);
	        }
	    patchPanel.get().setPorts(ports);
	    patchPanelRepository.save(patchPanel.get());
	    }
    }
}
