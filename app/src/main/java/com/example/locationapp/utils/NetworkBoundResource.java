package com.example.locationapp.utils;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.locationapp.data.sources.model.BaseRootResponse;
import com.example.locationapp.data.sources.model.preferlocation.Root;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class NetworkBoundResource<ResultType, RequestType extends BaseRootResponse> {
    private final AppExecutors appExecutors;
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        init();
    }

    private void init() {
        setValue(new Resource.Loading<>(null));
        LiveData<ResultType> dbSource = loadFromDb();

        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> setValue(new Resource.Success<>(newData)));
            }
        });
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        result.addSource(dbSource, newData -> setValue(new Resource.Loading<>(newData)));
        createCall().enqueue(new Callback<RequestType>() {
            @Override
            public void onResponse(Call<RequestType> call, Response<RequestType> response) {
                result.removeSource(dbSource);
                if (response.body() == null) {
                    result.addSource(dbSource, newData -> setValue(new Resource.Error<>(newData, response.message())));
                } else if (response.body().getError_code() != 0) {
                    result.addSource(dbSource, newData -> setValue(new Resource.Error<>(newData, response.body().getError_message())));
                } else {
                    saveResultAndReInit(response.body());
                }
            }

            @Override
            public void onFailure(Call<RequestType> call, Throwable t) {
                onFetchFailed();
                result.removeSource(dbSource);
                result.addSource(dbSource, newData -> setValue(new Resource.Error<>(newData, t.getMessage())));
            }
        });
    }

    @MainThread
    private void saveResultAndReInit(RequestType response) {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                saveCallResult(response);

                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        result.addSource(loadFromDb(), newData -> setValue(new Resource.Success<>(newData)));
                    }
                });
            }
        });
    }

    private void setValue(Resource<ResultType> newValue) {
        if (result.getValue() != newValue) {
            result.setValue(newValue);
        }
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract Call<RequestType> createCall();

    @MainThread
    protected void onFetchFailed() {
    }

    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return result;
    }
}