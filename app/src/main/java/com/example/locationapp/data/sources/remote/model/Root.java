package com.example.locationapp.data.sources.remote.model;

public class Root {
    private int error_code;
    private String error_message;
    private Data data;

    public Root(int error_code, String error_message, Data data) {
        this.error_code = error_code;
        this.error_message = error_message;
        this.data = data;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
