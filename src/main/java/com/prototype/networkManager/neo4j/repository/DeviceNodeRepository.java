package com.prototype.networkManager.neo4j.repository;

import com.prototype.networkManager.neo4j.domain.DeviceNode;
import com.prototype.networkManager.neo4j.domain.Port;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface DeviceNodeRepository extends PagingAndSortingRepository<DeviceNode, Long> {

    @Query("MATCH (p)-[:IS_PORT]->(n) where id(n)={0} optional match ()-[connections:CONNECTION]-(p)-[:IS_PORT]->(n) " +
            "where id(n)={0} return distinct id(p) as id, p.portNumber as portNumber, p.devicePlugged as devicePlugged, p.portOnTheUpperElement as portOnTheUpperElement, collect(connections) as connections")
    Iterable<Port> getPorts(Long id);

}
