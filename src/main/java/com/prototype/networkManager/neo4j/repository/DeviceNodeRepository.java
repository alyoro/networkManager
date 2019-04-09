package com.prototype.networkManager.neo4j.repository;

import com.prototype.networkManager.neo4j.domain.DeviceNode;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface DeviceNodeRepository extends PagingAndSortingRepository<DeviceNode, Long> {
}
