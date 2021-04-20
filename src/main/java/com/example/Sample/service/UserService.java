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

@Service
public class UserService implements IUserService {

    @Autowired(required = true)
    UserRepository userRepository;

    @Value("${accepted.headers}")
    private String acceptedHeaders;
    @Value("${accepted.reqParams}")
    private String acceptedRequestParams;

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

    private CacheKeyBuilder createCacheKey(User u,
                                           Map<String, String> requestParams,
                                           MultiValueMap<String, String> headers) {
        //sort the headers
        Map<String, String> sortedHeaders = new TreeMap<>();
        List<String> headersList = Arrays.asList(acceptedHeaders.split(","));
        for (String header : headersList) {
            List<String> requestHeaderValue = headers.get(header);
            if (requestHeaderValue != null) {
                sortedHeaders.put(header, requestHeaderValue.toString());
            }
        }

        System.out.println(acceptedHeaders);
        System.out.println(acceptedRequestParams);
        //sort the request params
        Map<String, String> sortedRequestParams = new TreeMap<>();
        List<String> reqParamList = Arrays.asList(acceptedRequestParams.split(","));

        reqParamList.forEach((reqParam) -> {
            String paramValue = requestParams.get(reqParam);
            if (paramValue != null) {
                sortedRequestParams.put(reqParam, paramValue);
            }
        });
        CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();
        cacheKeyBuilder.setHeaders(sortedHeaders);
        cacheKeyBuilder.setRequestParams(sortedRequestParams);
        cacheKeyBuilder.setRequestBody(u);
        System.out.println("CacheKeyBuilder == " + cacheKeyBuilder);
        return cacheKeyBuilder;
    }

}
