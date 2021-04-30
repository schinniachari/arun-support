package com.example.Sample.service;

import com.example.Sample.model.User;
import com.example.Sample.repository.UserRepository;
import com.example.Sample.util.CacheKeyBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * UserService class implements the IUserService, Handles the Business login.
 */
@Service
@Slf4j
public class UserService implements IUserService {

    @Autowired(required = true)
    UserRepository userRepository;

    /**
     * Filter out the Request Headers based on the list provided in this acceptedHeaders
     */
    @Value("${accepted.headers}")
    private String acceptedHeaders;
    /**
     * Filter out the Request Headers based on the list provided in this acceptedHeaders
     */
    @Value("${accepted.reqParams}")
    private String acceptedRequestParams;

    /**
     * saveUser(
     *
     * @param user,
     * @param requestParams,
     * @param headers,
     * @param requestURI)    Saves the user object into DB
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public Mono<User> saveUser(User user, Map<String, String> requestParams,
                               MultiValueMap<String, String> headers, String requestURI) throws JsonProcessingException {
        CacheKeyBuilder cacheKey = createCacheKey(user, requestParams, headers);
        ObjectMapper objectMapper = new ObjectMapper();
        String cacheKeyJson = objectMapper.writeValueAsString(cacheKey);
        //Append the Resource Name and Id  to the above string
        cacheKeyJson = cacheKeyJson + getResourceNameAndId(requestURI);

        Mono<User> monoUser = userRepository.save(user);
        return monoUser;
    }


    /**
     * getResourceNameAndId(
     * @param requestURI)
     *
     * For a particular URI this method will return the ResourceName and the ResourceId
     * Below are some examples
     * @return
     * For input app/v1/1 output= app1
     * app/1          app1
     *
     */
    private String getResourceNameAndId(String requestURI) {
        if (requestURI != null) {
            String[] array = requestURI.split("/");
            String resourceName = array[0];
            String resourceId = array[array.length - 1];
            return resourceName + resourceId;
        }
        return "";
    }

    /**
     * createCacheKey
     * @param user,
     * @param requestParams,
     * @param requestHeaders)
     * Builds the CacheKeyBuilder based on the RequestParams, RequestHeaders and RequestBody.
     *
     * @return
     */
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

        log.info("acceptedHeaders -> " + acceptedHeaders);
        log.info("acceptedRequestParams -> " + acceptedRequestParams);

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
        log.info("CacheKeyBuilder -> " + cacheKeyBuilder);
        return cacheKeyBuilder;
    }

}
