package com.example.karaktergenshinimpact.response;

public class APIError {

    private int statusCode;
    private String message;
    private String endpoint;

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getEndpoint() {
        return endpoint;
    }
}