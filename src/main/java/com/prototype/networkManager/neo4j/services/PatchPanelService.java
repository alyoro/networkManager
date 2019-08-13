package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.PatchPanel;
import com.prototype.networkManager.neo4j.exceptions.PatchPanelNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;

public interface PatchPanelService {

    PatchPanel getPatchPanel(Long id) throws PatchPanelNotFoundException;

    Iterable<PatchPanel> getPatchPanels();

    void deletePatchPanel(Long id) throws PatchPanelNotFoundException, PortNotFoundException;

    PatchPanel createPatchPanel(PatchPanel patchPanel);

    PatchPanel updatePatchPanel(Long id, PatchPanel patchPanel) throws PatchPanelNotFoundException;

    String createPatchPanelReport(Long id) throws PatchPanelNotFoundException;
}

