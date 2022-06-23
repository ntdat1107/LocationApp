package com.example.locationapp.data.sources.remote.model.preferlocation;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Root {
    @SerializedName("error_code")
    private int error_code;
    @SerializedName("error_message")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Root root = (Root) o;
        return error_code == root.error_code && error_message.equals(root.error_message) && data.equals(root.data);
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
