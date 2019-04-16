package com.prototype.networkManager.neo4j.repository;

import com.prototype.networkManager.neo4j.domain.DeviceNode;
import com.prototype.networkManager.neo4j.domain.Port;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Map;

@RepositoryRestResource(exported = false)
public interface DeviceNodeRepository extends PagingAndSortingRepository<DeviceNode, Long> {

    @Query("MATCH (p)-[:IS_PORT]->(n) where id(n)={0} optional match ()-[connections:CONNECTION]-(p)-[:IS_PORT]->(n) " +
            "where id(n)={0} return distinct id(p) as id, p.portNumber as portNumber, p.devicePlugged as devicePlugged, p.portOnTheUpperElement as portOnTheUpperElement, collect(connections) as connections")
    Iterable<Port> getPorts(Long id);

    @Query("MATCH (p:Port)-[:IS_PORT]->(device) WHERE id(p)={0} RETURN toString([label in labels(device) WHERE label<>'Node' AND label<>'DeviceNode'][0]) AS DeviceType")
    List<Map<String,Object>> getDeviceNodeTypeByIdOfPort(Long portId);

    @Query("MATCH(devices) WHERE {0} IN labels(devices) RETURN count(devices) AS count")
    Integer getNumberOfDevicesByType(String deviceType);

    @Query("MATCH (devices)<-[:IS_PORT]-(:Port)<-[:CONNECTION]-(:Port)-[:IS_PORT]->(device) WHERE ID(device)={0} RETURN devices")
    Iterable<DeviceNode> getDevicesLevelUp(Long deviceId);

    @Query("MATCH (devices)<-[:IS_PORT]-(:Port)-[:CONNECTION]->(:Port)-[:IS_PORT]->(device) WHERE ID(device)={0} RETURN devices")
    Iterable<DeviceNode> getDevicesLevelDown(Long deviceId);


}
