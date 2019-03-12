package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.PatchPanel;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.PatchPanelNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PortNumberAlreadyInListException;


public interface PatchPanelService {

    PatchPanel getPatchPanel(Long id) throws PatchPanelNotFoundException;
    Iterable<PatchPanel> getPatchPanels();
    void deletePatchPanel(Long id) throws PatchPanelNotFoundException;
    PatchPanel createPatchPanel(PatchPanel patchPanel);

    void addPort(Long id, Port port) throws PortNumberAlreadyInListException, PatchPanelNotFoundException;
}

