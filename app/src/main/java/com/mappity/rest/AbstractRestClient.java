package com.mappity.rest;

import com.google.gson.Gson;

import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class AbstractRestClient implements RestClient {
    protected static final Gson GSON = new Gson();
    private static final String TAG = "AbstractRestClient";
    private static final String APP_JSON = "application/json; charset=utf-8";
    private static final MediaType JSON = MediaType.parse(APP_JSON);
    protected OkHttpClient client;

    @Override
    public Response get(final String url, final Map<String, String> headers) {
        final Request.Builder builder = new Request.Builder();
        if (headers != null && !headers.isEmpty()) {
            final Set<Map.Entry<String, String>> entries = headers.entrySet();
            for (final Map.Entry<String, String> entry : entries) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }

        final Request request = builder.url(url).build();

        return execute(request);
    }

    @Override
    public Response post(final String url, final String json) {
        final Request request = new Request.Builder().url(url).post(
                RequestBody.create(JSON, json)).build();

        return execute(request);
    }

    private Response execute(final Request request) {
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (final IOException e) {
            Log.d(TAG, "Exception while executing call for url: " +
                    request.url(), e);
        }

        return response;
    }
}