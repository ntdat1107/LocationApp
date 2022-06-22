package com.example.locationapp.data.sources.remote.model.detaillocation;

import com.google.gson.annotations.SerializedName;

public class RootDetail {
    @SerializedName("error_code")
    private int error_code;
    @SerializedName("error_message")
    private String error_message;
    private DataDetail data;

    public RootDetail(int error_code, String error_message, DataDetail data) {
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

    public DataDetail getData() {
        return data;
    }

    public void setData(DataDetail dataDetail) {
        this.data = dataDetail;
    }
}