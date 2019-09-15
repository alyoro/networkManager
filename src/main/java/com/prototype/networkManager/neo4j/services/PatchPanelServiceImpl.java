package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Connection;
import com.prototype.networkManager.neo4j.domain.PatchPanel;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.PatchPanelNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.repository.PatchPanelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
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
        Iterable<PatchPanel> patchPanels = patchPanelRepository.findAll(2);
        patchPanels.forEach(patchPanel -> patchPanel.getPorts().forEach(port -> {
            if (port.getConnections() != null)
                port.getConnections().sort(Comparator.comparing(Connection::getConnectionType));
        }));

        return patchPanels;
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
        patchPanel.setPorts(portService.createMultiplePorts(patchPanel.getNumberOfPorts(), false));
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

    @Override
    public String createPatchPanelReport(Long id) throws PatchPanelNotFoundException {
        PatchPanel patchPanel = this.getPatchPanel(id);

        HashMap<String, String> patchPanelProps = new HashMap<>();
        patchPanelProps.put("Id", patchPanel.getId().toString());
        patchPanelProps.put("Identifier", patchPanel.getIdentifier());
        patchPanelProps.put("Number of Ports", String.valueOf(patchPanel.getNumberOfPorts()));
        patchPanelProps.put("Building", patchPanel.getBuilding());
        patchPanelProps.put("Room", patchPanel.getRoom());
        patchPanelProps.put("Localization", patchPanel.getLocalization());
        patchPanelProps.put("Description", patchPanel.getDescription());

        StringBuilder text = new StringBuilder();
        text.append("Patch Panel - " + patchPanelProps.get("Identifier") + " - Properties\n");
        for (Map.Entry<String, String> entry : patchPanelProps.entrySet()) {
            text.append("\t" + entry.getKey() + ": " + entry.getValue() + "\n");
        }
        return text.toString();
    }

    @Override
    public String createPatchPanelsReport() {
        Iterable<PatchPanel> patchPanels = this.getPatchPanels();

        StringBuilder text = new StringBuilder();

        text.append("Patch Panels - All\n");
        text.append("Id\t\tIdentifier\t\tNo. Ports\t\tBuilding\t\tRoom\t\tLocalization\t\tDescription\n\n");

        for (PatchPanel pp : patchPanels) {
            text.append(
                    pp.getId() + getSepList() + pp.getIdentifier() + getSepList() + pp.getNumberOfPorts() +
                            getSepList() + getSepList() + pp.getBuilding() + getSepList() + "\t" + pp.getRoom() + "\t" +
                            getSepList() + pp.getLocalization() + getSepList() + pp.getDescription() + "\n"
            );
        }

        return text.toString();
    }

    private String getSepList() {
        return "\t\t";
    }

}
