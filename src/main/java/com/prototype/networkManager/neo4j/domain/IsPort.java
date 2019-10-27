package com.prototype.networkManager.neo4j.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.RelationshipEntity;
@Data
@NoArgsConstructor
@RelationshipEntity(type = "IS_PORT")
public class IsPort {
    private String name;
}
