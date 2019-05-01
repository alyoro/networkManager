package com.prototype.networkManager.neo4j.exceptions;

public class ConnectionNotFoundException extends Exception {
    public ConnectionNotFoundException(String message) {
        super(message);
    }
}
