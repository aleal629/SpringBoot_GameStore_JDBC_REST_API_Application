package com.example.SpringBoot_GameStore_JDBC_REST_API.customExceptions;

/**
 * Custom exception for incomplete information sent by client to the Game Store Service
 */
public class InformationMissingException extends IllegalArgumentException {
    public InformationMissingException(String errorMessage) {
        super(errorMessage);
    }
}
