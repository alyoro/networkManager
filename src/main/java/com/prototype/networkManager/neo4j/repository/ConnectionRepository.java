package com.prototype.networkManager.neo4j.repository;

import com.prototype.networkManager.neo4j.domain.Connection;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ConnectionRepository extends PagingAndSortingRepository<Connection, Long> {
}
