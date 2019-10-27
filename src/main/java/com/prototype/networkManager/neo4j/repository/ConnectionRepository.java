package com.prototype.networkManager.neo4j.repository;

import com.prototype.networkManager.neo4j.domain.Connection;
import com.prototype.networkManager.neo4j.domain.Port;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Map;

@RepositoryRestResource(exported = false)
public interface ConnectionRepository extends PagingAndSortingRepository<Connection, Long> {

    @Deprecated(forRemoval = true)
    @Query("MATCH (pM:Port)-[:IS_PORT]->(deviceMaster),(pS:Port) where ID(pM)={0} and ID(pS)={1} " +
            "CREATE (pS)-[c:CONNECTION {vlans: {2}}]->(pM) " +
            "SET pS.devicePlugged = toString([x in labels(deviceMaster) where x<>'Node' and x<>'DeviceNode'][0]) " +
            "SET pS.portOnTheUpperElement = toString(pM.portNumber) " +
            "RETURN c")
    Iterable<Map<String, Object>> saveConnection(Long portIdMaster, Long portIdSlave, List<String> vlans);

    /**
     * Returns Map of two ports - portMaster and portSlave
     *
     * @param id Of connection to return start and end node
     * @return Returns Map of two ports - portMaster and portSlave
     */
    @Query("MATCH(startPort:Port)-[c:CONNECTION]->(endPort:Port) WHERE ID(c)={0} RETURN startPort AS portSlave, endPort AS portMaster")
    List<Map<String, Port>> getStartAndEndNode(Long id);
}
