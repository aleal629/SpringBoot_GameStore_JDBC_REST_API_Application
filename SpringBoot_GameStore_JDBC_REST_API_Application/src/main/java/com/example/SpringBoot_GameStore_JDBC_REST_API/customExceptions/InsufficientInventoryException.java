package com.example.SpringBoot_GameStore_JDBC_REST_API.customExceptions;

/**
 * Custom exception for insufficient inventory on purchase requests sent by client to the Game Store Service
 */
public class InsufficientInventoryException extends IllegalArgumentException {
    public InsufficientInventoryException(String errorMessage) {
        super(errorMessage);
    }
}
