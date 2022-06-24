package com.example.locationapp.data.sources.model;

import com.google.gson.annotations.SerializedName;

public abstract class BaseRoot <T> {
    @SerializedName("error_message")
    private String error_message;

    @SerializedName("error_code")
    private int error_code;

    @SerializedName("data")
    private T data;

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
