package com.prototype.networkManager.neo4j.repository;

import com.prototype.networkManager.neo4j.domain.PatchPanel;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface PatchPanelRepository extends PagingAndSortingRepository<PatchPanel, Long> {
    Iterable<PatchPanel> findAll(@Depth int depth);
}
