package com.prototype.networkManager.neo4j.domain;

import com.prototype.networkManager.neo4j.domain.enums.ConnectionType;
import org.neo4j.ogm.annotation.RelationshipEntity;

@RelationshipEntity(type = "CONNECTION")
public class Connection extends Edge {

    private Long portIdStart;
    private Long portIdEnd;
    private ConnectionType connectionType;

    public Connection() {
    }

    public Connection(Port pM, Port pS) {
        super();
        this.setStartNode(pS);
        this.setEndNode(pM);
    }

    public Connection(Long portIdStart, Long portIdEnd, ConnectionType connectionType) {
        this.portIdStart = portIdStart;
        this.portIdEnd = portIdEnd;
        this.connectionType = connectionType;
    }

    public Long getPortIdStart() {
        return portIdStart;
    }

    public void setPortIdStart(Long portIdStart) {
        this.portIdStart = portIdStart;
    }

    public Long getPortIdEnd() {
        return portIdEnd;
    }

    public void setPortIdEnd(Long portIdEnd) {
        this.portIdEnd = portIdEnd;
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }
}
