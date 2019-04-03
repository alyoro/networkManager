package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.PatchPanel;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.PatchPanelNotFoundException;
import com.prototype.networkManager.neo4j.repository.PatchPanelRepository;
import com.prototype.networkManager.neo4j.repository.PortRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PatchPanelServiceImpl implements PatchPanelService{

    private final PatchPanelRepository patchPanelRepository;

    private final PortRepository portRepository;

    public PatchPanelServiceImpl(PatchPanelRepository patchPanelRepository, PortRepository portRepository) {
        this.patchPanelRepository = patchPanelRepository;
        this.portRepository = portRepository;
    }

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
        Optional<PatchPanel> patchPanelOptional = patchPanelRepository.findById(id);
        if(patchPanelOptional.isPresent()){
            if(!patchPanelOptional.get().getPorts().isEmpty()) {
                for (Port p : patchPanelOptional.get().getPorts()) {
                    portRepository.delete(p);
                }
            }
            patchPanelRepository.deleteById(id);
        }else{
            throw new PatchPanelNotFoundException("PatchPanel with id: "+id+" not found.");
        }
    }

    @Override
    public PatchPanel createPatchPanel(PatchPanel patchPanel){
        return patchPanelRepository.save(patchPanel);
    }
}
