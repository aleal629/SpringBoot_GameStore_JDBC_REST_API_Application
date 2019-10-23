package com.example.SpringBoot_GameStore_JDBC_REST_API.customExceptions;

/**
 * Custom exception for when Game Store service is unable to communicate with database server
 */
public class DatabaseUnreachableError extends IllegalArgumentException {
    public DatabaseUnreachableError(String errorMessage) {
        super(errorMessage);
    }
}
