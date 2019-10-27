package com.prototype.networkManager.neo4j.repository;

import com.prototype.networkManager.neo4j.domain.AccessPoint;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface AccessPointRepository extends PagingAndSortingRepository<AccessPoint, Long> {

    /**
     * Returns all Access Points in database
     *
     * @param depth Neo4j parameter - how deep to search
     * @return List of all AccessPoints
     */
    Iterable<AccessPoint> findAll(@Depth int depth);
}
