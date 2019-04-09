package com.prototype.networkManager.neo4j.repository;

import com.prototype.networkManager.neo4j.domain.Node;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface NodeRepository extends PagingAndSortingRepository<Node, Long> {
}
