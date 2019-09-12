package com.prototype.networkManager.neo4j.repository;

import com.prototype.networkManager.neo4j.domain.DeviceNode;
import com.prototype.networkManager.neo4j.domain.Port;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface DeviceNodeRepository extends PagingAndSortingRepository<DeviceNode, Long> {

    /**
     * Return all ports that are signed for specific id
     *
     * @param id of device which one ports gonna be returned
     * @return List of all Ports assigned for Device
     */
    @Query("MATCH (p)-[:IS_PORT]->(n) where id(n)={0} optional match ()-[connections:CONNECTION]-(p)-[:IS_PORT]->(n) " +
            "where id(n)={0} return distinct id(p) as id, p.portNumber as portNumber, p.devicePlugged as devicePlugged, p.portOnTheUpperElement as portOnTheUpperElement, collect(connections) as connections")
    Iterable<Port> getPorts(Long id);

    /**
     * Return device type by portId
     * @param portId of which one device type gonna be returned
     * @return Map of Device type that has specific port
     */
    @Query("MATCH (p:Port)-[:IS_PORT]->(device) WHERE id(p)={0} RETURN toString([label in labels(device) WHERE label<>'Node' AND label<>'DeviceNode'][0]) AS DeviceType")
    List<Map<String, Object>> getDeviceNodeTypeByIdOfPort(Long portId);

    /**
     * Return Device which have port with specific portId
     * @param portId of which one device type gonna be returned
     * @return Optional of generic device type
     */
    @Query("MATCH (p:Port)-[:IS_PORT]->(device) WHERE id(p)={0} RETURN device")
    Optional<DeviceNode> getDeviceNodeByIdOfPort(Long portId);

    /**
     * Return number of devices in database with specific Type(e.g. PatchPanel)
     * @param deviceType
     * @return Number of devices
     */
    @Query("MATCH(devices) WHERE {0} IN labels(devices) RETURN count(devices) AS count")
    Integer getNumberOfDevicesByType(String deviceType);

    /**
     * FIND BY ID
     * @param id of device
     * @param depth neo4j property
     * @return optional of generic device type
     */
    Optional<DeviceNode> findById(Long id, @Depth int depth);

}
