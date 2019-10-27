package com.prototype.networkManager.neo4j.domain;

import com.prototype.networkManager.neo4j.domain.enums.ConnectionType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.RelationshipEntity;

@Data
@NoArgsConstructor
@RelationshipEntity(type = "CONNECTION")
public class Connection extends Edge {

    private Long portIdStart;
    private Long portIdEnd;
    private ConnectionType connectionType;
}
