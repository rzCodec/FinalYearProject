package com.example.devandrin.myapplication;

/**
 * Created by Devandrin on 2017/04/17.
 */

public enum NETWORK_ERROR_CODES {
    SUCCESS(200),
    BAD_URL(400),
    FORBIDDEN(404),
    ID_NOT_FOUND_IN_DATABASE(404),
    RATE_LIMIT_EXCEEDED(429),
    SERVER_SIDE_ERROR(500);
    int value;

    NETWORK_ERROR_CODES(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
