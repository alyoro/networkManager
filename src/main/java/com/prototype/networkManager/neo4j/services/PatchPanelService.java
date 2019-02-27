package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.PatchPanel;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.PortNumberAlreadyInListException;


public interface PatchPanelService {

    void addPatchPanel(PatchPanel patchPanel);

    Iterable<PatchPanel> findAll();

    void addPort(Long id, Port port) throws PortNumberAlreadyInListException;
}

