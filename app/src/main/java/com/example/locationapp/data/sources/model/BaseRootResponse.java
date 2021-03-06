package com.example.locationapp.data.sources.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public abstract class BaseRootResponse <T> implements Serializable {

    @SerializedName("error_code")
    private int error_code;
    @SerializedName("error_message")
    private String error_message;
    private T data;

    public BaseRootResponse(int error_code, String error_message) {
        this.error_code = error_code;
        this.error_message = error_message;
    }

    public BaseRootResponse(int error_code, String error_message, T data) {
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

    public T getData() {
        return data;
    }

    public void setData(T dataDetail) {
        this.data = dataDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseRootResponse<?> that = (BaseRootResponse<?>) o;
        return error_code == that.error_code && Objects.equals(error_message, that.error_message) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error_code, error_message, data);
    }

    @NonNull
    @Override
    public String toString() {
        return "{\n" +
                "\"error_code\": 0,\n" +
                "\"error_message\": \"\",\n" +
                this.data.toString() +
                "\n}";
    }
}
