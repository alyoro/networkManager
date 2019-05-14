package com.prototype.networkManager.neo4j.repository;

import com.prototype.networkManager.neo4j.domain.Node;
import com.prototype.networkManager.neo4j.domain.PortSpeed;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface NodeRepository extends PagingAndSortingRepository<Node, Long> {

    @Query("MATCH(n:PortSpeed) return n")
    Optional<PortSpeed> getPortSpeedTypes();

    @Query("MATCH(n:PortSpeed) SET n.names = {0}")
    void savePortSpeedTypes(List<String> names);

}
