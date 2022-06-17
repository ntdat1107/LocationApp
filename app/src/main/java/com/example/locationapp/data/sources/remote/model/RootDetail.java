package com.example.locationapp.data.sources.remote.model;

public class RootDetail {
    private int error_code;
    private String error_message;
    private DataDetail data;

    public RootDetail(int error_code, String error_message) {
        this.error_code = error_code;
        this.error_message = error_message;
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

    public DataDetail getData() {
        return data;
    }

    public void setData(DataDetail dataDetail) {
        this.data = dataDetail;
    }
}
