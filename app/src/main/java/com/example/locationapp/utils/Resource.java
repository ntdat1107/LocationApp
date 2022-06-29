package com.example.locationapp.utils;

public class Resource<T> {
    protected Status status;
    protected T data;
    protected String error;

    protected Resource(Status status, T data, String error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public static class Initial<T> extends Resource<T> {
        public Initial() {
            super(Status.INITIAL, null, null);
        }
    }

    public static class Loading<T> extends Resource<T> {
        public Loading(T data) {
            super(Status.LOADING, data, null);
        }
    }

    public static class Success<T> extends Resource<T> {
        public Success(T data) {
            super(Status.SUCCESS, data, null);
        }
    }

    public static class Error<T> extends Resource<T> {
        public Error(T data, String error) {
            super(Status.ERROR, data, error);
        }
    }
}

