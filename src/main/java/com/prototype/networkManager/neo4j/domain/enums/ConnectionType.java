package com.prototype.networkManager.neo4j.domain.enums;

/**
 * Describes two individual type of possible connection.
 * Most common is SOCKET used in almost every connection.
 * PLUG is used to indicate inner part of the PatchPanel.
 */
public enum ConnectionType {
    SOCKET, PLUG
}
