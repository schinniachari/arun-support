package com.example.Sample.util;

import java.util.Map;

public class CacheKeyBuilder {
    private Map<String, String> headers;
    private Map<String, String> requestParams;
    private Object requestBody;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(Map<String, String> requestParams) {
        this.requestParams = requestParams;
    }

    public Object getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(Object requestBody) {
        this.requestBody = requestBody;
    }

    @Override
    public String toString() {
        return "CacheKeyBuilder{" +
                "headers=" + headers +
                ", requestParams=" + requestParams +
                ", requestBody=" + requestBody +
                '}';
    }
}

