package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.PatchPanel;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.repository.PatchPanelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PatchPanelServiceImpl implements PatchPanelService{

    @Autowired
    PatchPanelRepository patchPanelRepository;

    public void addPatchPanel(PatchPanel patchPanel){
        patchPanelRepository.save(patchPanel);
    }

    public Iterable<PatchPanel> findAll(){
        return patchPanelRepository.findAll();
    }

    @Override
    public void addPort(Long id, Port port) {
        System.out.println(port.getDevicePlugged());

        Optional<PatchPanel> patchPanel = patchPanelRepository.findById(id);
        if(patchPanel == null) {
            return;
        }
        else {
            patchPanel.get().addPort(port);
            patchPanelRepository.save(patchPanel.get());
        }
    }
}
