package com.mappity.rest;

import java.util.Map;

import okhttp3.Response;

public interface RestClient {
    Response get(final String url, final Map<String, String> headers);

    Response post(String url, String json);
}