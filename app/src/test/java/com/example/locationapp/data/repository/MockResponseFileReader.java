package com.example.locationapp.data.repository;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.Objects;

public class MockResponseFileReader {
    String content;

    public MockResponseFileReader() {
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader()).getResourceAsStream("success_response.json"));
        try {
            reader.read(CharBuffer.wrap(content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
