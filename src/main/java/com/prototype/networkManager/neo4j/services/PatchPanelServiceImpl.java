package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.PatchPanel;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.PatchPanelNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.repository.PatchPanelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PatchPanelServiceImpl implements PatchPanelService {

    private final PatchPanelRepository patchPanelRepository;

    private final PortService portService;

    public PatchPanelServiceImpl(PatchPanelRepository patchPanelRepository, PortService portService) {
        this.patchPanelRepository = patchPanelRepository;
        this.portService = portService;
    }

    @Override
    public PatchPanel getPatchPanel(Long id) throws PatchPanelNotFoundException {
        Optional<PatchPanel> patchPanel = patchPanelRepository.findById(id);
        if (patchPanel.isPresent()) {
            return patchPanel.get();
        } else {
            throw new PatchPanelNotFoundException("PatchPanel with id: " + id + " not found.");
        }
    }

    @Override
    public Iterable<PatchPanel> getPatchPanels() {
        return patchPanelRepository.findAll(2);
    }

    @Override
    public void deletePatchPanel(Long id) throws PatchPanelNotFoundException, PortNotFoundException {
        Optional<PatchPanel> patchPanelOptional = patchPanelRepository.findById(id);
        if (patchPanelOptional.isPresent()) {
            if (!(patchPanelOptional.get().getPorts() == null)) {
                for (Port p : patchPanelOptional.get().getPorts()) {
                    portService.deletePort(p.getId());
                }
            }
            patchPanelRepository.deleteById(id);
        } else {
            throw new PatchPanelNotFoundException("PatchPanel with id: " + id + " not found.");
        }
    }

    @Override
    public PatchPanel createPatchPanel(PatchPanel patchPanel) {
        patchPanel.setPorts(portService.createMultiplePorts(patchPanel.getNumberOfPorts()));
        return patchPanelRepository.save(patchPanel);
    }

    @Override
    public PatchPanel updatePatchPanel(Long id, PatchPanel patchPanel) throws PatchPanelNotFoundException {
        Optional<PatchPanel> patchPanelOptional = patchPanelRepository.findById(id);
        if (patchPanelOptional.isPresent()) {
            patchPanelOptional.get().setBuilding(patchPanel.getBuilding());
            patchPanelOptional.get().setRoom(patchPanel.getRoom());
            patchPanelOptional.get().setIdentifier(patchPanel.getIdentifier());
            patchPanelOptional.get().setLocalization(patchPanel.getLocalization());
            patchPanelOptional.get().setDescription(patchPanel.getDescription());

            return patchPanelRepository.save(patchPanelOptional.get());
        } else {
            throw new PatchPanelNotFoundException("PatchPanel with id: " + id + " not found.");
        }
    }
}
