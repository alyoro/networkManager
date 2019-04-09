package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.PatchPanel;
import com.prototype.networkManager.neo4j.exceptions.PatchPanelNotFoundException;

public interface PatchPanelService {

    PatchPanel getPatchPanel(Long id) throws PatchPanelNotFoundException;
    Iterable<PatchPanel> getPatchPanels();
    void deletePatchPanel(Long id) throws PatchPanelNotFoundException;
    PatchPanel createPatchPanel(PatchPanel patchPanel);
}

