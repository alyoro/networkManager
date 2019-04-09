package com.prototype.networkManager.neo4j.repository;

import com.prototype.networkManager.neo4j.domain.Port;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface PortRepository extends PagingAndSortingRepository<Port, Long> {

    @Query("MATCH (p:Port)-[:IS_PORT]->(n) WHERE ID(n)={0} RETURN p")
    Iterable<Port> getPorts(Long id);
}
