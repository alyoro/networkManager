package com.prototype.networkManager.neo4j.repository;

import com.prototype.networkManager.neo4j.domain.Switch;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface SwitchRepository extends PagingAndSortingRepository<Switch, Long> {
    Iterable<Switch> findAll(@Depth int depth);
}
