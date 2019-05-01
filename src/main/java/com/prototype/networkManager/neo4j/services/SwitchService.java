package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Switch;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.SwitchNotFoundException;

public interface SwitchService {
    Switch getSwitch(Long id) throws SwitchNotFoundException;

    Iterable<Switch> getSwitches();

    void deleteSwitch(Long id) throws SwitchNotFoundException, PortNotFoundException;

    Switch createSwitch(Switch switchDevice);
}
