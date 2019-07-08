package com.prototype.networkManager.neo4j.repository;

import com.prototype.networkManager.neo4j.domain.Vlans;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface VlansRepository extends PagingAndSortingRepository <Vlans, Long> {

    @Query("MATCH(n:Vlans) return n")
    Optional<Vlans> getVlansTypes();

    @Query("MATCH(n:Vlans) SET n.names = {0}")
    void saveVlansTypes(List<String> names);
}
