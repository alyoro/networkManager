package com.prototype.networkManager.neo4j.repository;

import com.prototype.networkManager.neo4j.domain.Port;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.ArrayList;

@RepositoryRestResource(exported = false)
public interface PortRepository extends PagingAndSortingRepository<Port, Long> {

//    @Query("match (port)-[:IS_PORT]->(n) WHERE ID(n)={0} optional match ()-[connections:CONNECTION]-(port)-[:IS_PORT]->(n)" +
//            " WHERE ID(n)={0}  with {id:id(port), devicePlugged:port.devicePlugged, portOnTheUpperElement:port.portOnTheUpperElement," +
//            " portNumber:port.portNumber,  connections:connections } as port return collect(port) as ports")
//    ArrayList<Port> getPorts(Long id, @Depth int depth);
}
