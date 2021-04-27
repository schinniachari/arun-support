package com.example.Sample.service;

import com.example.Sample.model.User;
import com.example.Sample.repository.UserRepository;
import com.example.Sample.util.CacheKeyBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type User service.
 */
@Service
public class UserService implements IUserService {

    /**
     * The User repository.
     */
    @Autowired(required = true)
    UserRepository userRepository;

    @Value("${accepted.headers}")
    private String acceptedHeaders;
    @Value("${accepted.reqParams}")
    private String acceptedRequestParams;

    @Override
    public User saveUser(User user, Map<String, String> requestParams,
                         MultiValueMap<String, String> headers, String requestURI) throws JsonProcessingException {
        CacheKeyBuilder cacheKey = createCacheKey(user, requestParams, headers);
        ObjectMapper objectMapper = new ObjectMapper();
        String cacheKeyJson = objectMapper.writeValueAsString(cacheKey);
        //Append the Resource Name and Id  to the above string
        cacheKeyJson = cacheKeyJson + getResourceNameAndId(requestURI);
        userRepository.save(user);
        return user;
    }

    /**
     * For input app/v1/1 output= app1
     * app/1          app1
     **/
    private String getResourceNameAndId(String requestURI) {
        if (requestURI != null) {
            String[] array = requestURI.split("/");
            String resourceName = array[0];
            String resourceId = array[array.length - 1];
            return resourceName + resourceId;
        }
        return "";
    }

    private CacheKeyBuilder createCacheKey(User user,
                                           Map<String, String> requestParams,
                                           MultiValueMap<String, String> requestHeaders) {
        Map<String, String> filteredHeaders = new TreeMap<>();
        List<String> acceptedHeadersList = Arrays.asList(acceptedHeaders.split(","));
        for (Map.Entry<String, List<String>> eachRequestHeaderEntry : requestHeaders.entrySet()) {
            String key = eachRequestHeaderEntry.getKey();
            if (acceptedHeadersList.contains(key)) {
                filteredHeaders.put(key, eachRequestHeaderEntry.getValue().toString());
            }
        }

        System.out.println("acceptedHeaders -> " + acceptedHeaders);
        System.out.println("acceptedRequestParams -> " + acceptedRequestParams);

        Map<String, String> filteredRequestParams = new TreeMap<>();
        List<String> acceptedRequestParamsList = Arrays.asList(acceptedRequestParams.split(","));
        for (Map.Entry<String, String> eachRequestParamEntry : requestParams.entrySet()) {
            String key = eachRequestParamEntry.getKey();
            if (acceptedRequestParamsList.contains(key)) {
                filteredRequestParams.put(key, eachRequestParamEntry.getValue());
            }
        }

        CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();
        cacheKeyBuilder.setHeaders(filteredHeaders);
        cacheKeyBuilder.setRequestParams(filteredRequestParams);
        cacheKeyBuilder.setRequestBody(user);
        System.out.println("CacheKeyBuilder == " + cacheKeyBuilder);
        return cacheKeyBuilder;
    }

}
