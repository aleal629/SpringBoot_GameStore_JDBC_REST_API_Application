package com.example.SpringBoot_GameStore_JDBC_REST_API.customExceptions;

/**
 * Custom exception for invalid item ID sent by client to the Game Store Service
 */
public class InvalidItemIdNumber extends IllegalArgumentException {
    public InvalidItemIdNumber(String errorMessage) {
        super(errorMessage);
    }
}
