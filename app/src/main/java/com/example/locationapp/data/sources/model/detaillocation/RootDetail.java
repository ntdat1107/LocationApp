package com.example.locationapp.data.sources.model.detaillocation;

import androidx.annotation.NonNull;

import com.example.locationapp.data.sources.model.BaseRootResponse;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class RootDetail extends BaseRootResponse<DataDetail> {
    public RootDetail(int error_code, String error_message) {
        super(error_code, error_message);
    }
    public RootDetail(int error_code, String error_message, DataDetail data) {
        super(error_code, error_message, data);
    }
}
