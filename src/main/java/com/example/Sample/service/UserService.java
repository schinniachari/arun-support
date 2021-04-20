package com.example.Sample.service;

import com.example.Sample.model.User;
import com.example.Sample.repository.UserRepository;
import com.example.Sample.util.CacheKeyBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.util.TreeMap;

@Service
public class UserService implements IUserService {
    @Autowired(required=true)
    UserRepository userRepository;

    @Override
    public User saveUser(User user, Map<String, String> requestParams,
                         MultiValueMap<String, String> headers, StringBuffer requestURL) throws JsonProcessingException {
        CacheKeyBuilder cacheKey = createCacheKey(user, requestParams, headers);
        ObjectMapper objectMapper = new ObjectMapper();
        String userString = objectMapper.writeValueAsString(cacheKey);
        //Append the URL to the above string

        userRepository.save(user);
        return user;
    }

    private CacheKeyBuilder createCacheKey(User u, Map<String, String> requestParams,
                                           MultiValueMap<String, String> headers) {
        //sort the requestParams
        TreeMap<String, String> sortedRequestParams = new TreeMap<>();
        sortedRequestParams.putAll(requestParams);

        //sort the headers
        Map<String, String> mapOfHeaders = new TreeMap<>();
        headers.forEach((key, value) -> {
            mapOfHeaders.put(key, value.toString());
        });
        CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();
        cacheKeyBuilder.setHeaders(mapOfHeaders);
        cacheKeyBuilder.setRequestParams(requestParams);
        cacheKeyBuilder.setRequestBody(u);
        return cacheKeyBuilder;
    }

}
