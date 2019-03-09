package com.prototype.networkManager.neo4j.repository;

import com.prototype.networkManager.neo4j.domain.Connection;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ConnectionRepository extends PagingAndSortingRepository<Connection, Long> {

    @Query("MATCH (pM:Port),(pS:Port) CREATE (pS)-[c:CONNECTION {vlans: [{2}]}]->(pM) RETURN c")
    Connection saveConnection(Long portIdMaster, Long portIdSlave, String vlan);
}
