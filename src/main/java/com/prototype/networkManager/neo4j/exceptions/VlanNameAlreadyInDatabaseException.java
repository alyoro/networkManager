package com.prototype.networkManager.neo4j.exceptions;

public class VlanNameAlreadyInDatabaseException extends Exception {
    public VlanNameAlreadyInDatabaseException(String message) {
        super(message);
    }
}
