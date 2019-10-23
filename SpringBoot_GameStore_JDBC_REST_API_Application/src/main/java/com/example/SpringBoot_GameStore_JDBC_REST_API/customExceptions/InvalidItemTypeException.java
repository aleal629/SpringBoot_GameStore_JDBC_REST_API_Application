package com.example.SpringBoot_GameStore_JDBC_REST_API.customExceptions;

/**
 * Custom exception for invalid item types sent by client to the Game Store Service
 */
public class InvalidItemTypeException extends IllegalArgumentException {
    public InvalidItemTypeException(String errorMessage) {
        super(errorMessage);
    }
}
