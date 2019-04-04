package com.prototype.networkManager.neo4j.repository;

import com.prototype.networkManager.neo4j.domain.Connection;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ConnectionRepository extends PagingAndSortingRepository<Connection, Long> {

    @Query("MATCH (pM:Port)-[:IS_PORT]->(deviceMaster),(pS:Port) where ID(pM)={0} and ID(pS)={1} " +
            "CREATE (pS)-[c:CONNECTION {vlans: {2}}]->(pM) " +
            "SET pS.devicePlugged = toString([x in labels(deviceMaster) where x<>'Node' and x<>'DeviceNode'][0]) " +
            "SET pS.portOnTheUpperElement = toString(pM.portNumber) " +
            "RETURN c")
    Connection saveConnection(Long portIdMaster, Long portIdSlave, List<String> vlans);
}
