package com.prototype.networkManager.neo4j.exceptions;

public class MaximumPortNumberReachedException extends Exception {
    public MaximumPortNumberReachedException(String message){
        super(message);
    }
}
