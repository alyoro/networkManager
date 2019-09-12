package com.prototype.networkManager.neo4j.repository;

import com.prototype.networkManager.neo4j.domain.PortSpeed;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface PortSpeedRepository extends PagingAndSortingRepository<PortSpeed, Long> {

    /**
     * Return one node with property that stores all Port Speed names
     *
     * @return Optional of one node
     */
    @Query("MATCH(n:PortSpeed) return n")
    Optional<PortSpeed> getPortSpeedTypes();

    /**
     * Setting updated list of Port Speed names into one node which store it
     * @param names updated list of names
     */
    @Query("MATCH(n:PortSpeed) SET n.names = {0}")
    void savePortSpeedTypes(List<String> names);
}
