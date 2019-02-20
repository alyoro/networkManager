package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.PatchPanel;
import com.prototype.networkManager.neo4j.repository.PatchPanelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatchPanelServiceImpl implements PatchPanelService{

    @Autowired
    PatchPanelRepository patchPanelRepository;

    public void addPatchPanel(PatchPanel patchPanel){
        patchPanelRepository.save(patchPanel);
    }
}
