package com.example.Sample.util;

import java.util.Map;
import java.util.TreeMap;

public class CacheKeyBuilder {
    private Map<String, String> headers = new TreeMap<>();
    private Map<String, String> requestParams =new TreeMap<>();
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
}

