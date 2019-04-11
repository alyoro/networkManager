package com.prototype.networkManager.neo4j.services;

import java.util.List;

public interface InfoService {

    List<InfoServiceImpl.DeviceCount> countingDevices();

}
