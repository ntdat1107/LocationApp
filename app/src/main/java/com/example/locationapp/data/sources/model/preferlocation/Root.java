package com.example.locationapp.data.sources.model.preferlocation;

import androidx.annotation.NonNull;

import com.example.locationapp.data.sources.model.BaseRootResponse;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class Root extends BaseRootResponse<Data> {
    public Root(int error_code, String error_message) {
        super(error_code, error_message);
    }

    public Root(int error_code, String error_message, Data data) {
        super(error_code, error_message, data);
    }
}
